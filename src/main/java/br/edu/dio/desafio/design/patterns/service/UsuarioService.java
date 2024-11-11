package br.edu.dio.desafio.design.patterns.service;

import br.edu.dio.desafio.design.patterns.dto.EnderecoDTO;
import br.edu.dio.desafio.design.patterns.external.facade.ViaCepClient;
import br.edu.dio.desafio.design.patterns.model.Endereco;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import br.edu.dio.desafio.design.patterns.repository.AutenticacaoRepository;
import br.edu.dio.desafio.design.patterns.repository.UsuarioRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class UsuarioService {

    private final ViaCepClient viaCepClient;
    private final UsuarioRepository usuarioRepository;
    private final AutenticacaoRepository autenticacaoRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    private static final String ROLE_MANAGERS = "MANAGERS";

    public UsuarioService(ViaCepClient viaCepClient, UsuarioRepository usuarioRepository, AutenticacaoRepository autenticacaoRepository, PasswordEncoder encoder, ModelMapper modelMapper) {

        this.viaCepClient = viaCepClient;
        this.usuarioRepository = usuarioRepository;
        this.autenticacaoRepository = autenticacaoRepository;
        this.encoder = encoder;
        this.modelMapper = modelMapper;

    }

    @Transactional
    public void cadastrar(Usuario usuario){

        String pass = usuario.getAutenticacao().getPassword();

        //criptografando antes de salvar no banco
        usuario.getAutenticacao().setPassword(encoder.encode(pass));

        //atribuindo perfil USERS
        usuario.getAutenticacao().setRoles(List.of("USERS"));

        //consultando CEP na API ViaCEP
        try {

            EnderecoDTO enderecoDTO = viaCepClient.consultarCep(usuario.getEndereco().getCep());

            if (enderecoDTO == null || Optional.ofNullable(enderecoDTO.getErro()).orElse(false)) {

                throw new IllegalArgumentException("CEP não encontrado");

            }

            Endereco endereco = Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .logradouro(enderecoDTO.getLogradouro())
                    .bairro(enderecoDTO.getBairro())
                    .uf(enderecoDTO.getUf())
                    .estado(enderecoDTO.getEstado())
                    .numero(usuario.getEndereco().getNumero())
                    .complemento(usuario.getEndereco().getComplemento())
                    .build();

            usuario.setEndereco(endereco);

        } catch (IllegalArgumentException | FeignException e) {

            log.info("Erro ao consultar API ViaCEP. Username: {}. Message: {}", usuario.getAutenticacao().getUsername(), e.getMessage());

        }

        autenticacaoRepository.save(usuario.getAutenticacao());
        usuarioRepository.save(usuario);

    }

    public Usuario consultarUsuarioLogado() {

        String usuarioLogado = getUsuarioLogado();

        return usuarioRepository.findByUsername(usuarioLogado)
                .orElseThrow(() -> new UsernameNotFoundException(usuarioLogado + ": Username não encontrado"));

    }

    public List<Usuario> consultarTodos() {

        if (ehAdmin()) {
            return usuarioRepository.findAll();
        }

        throw new AccessDeniedException("Operação não permitida");

    }

    public Usuario consultarById(Integer id) {

        Usuario usuarioConsultado = usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        if ( ehAdmin() || ehCadastroProprio(usuarioConsultado) ) {
            return usuarioConsultado;
        }

        // lança exceção caso tenha tenha permissão de acesso
        throw new AccessDeniedException("Operação não permitida");

    }

    @Transactional
    public Usuario atualizar(Usuario usuarioParaAtualizar) {

        Usuario usuarioExistente = usuarioRepository.findById(usuarioParaAtualizar.getId())
                .orElseThrow(NoSuchElementException::new);

        if (ehAdmin() || ehCadastroProprio(usuarioExistente) ) {
            // atualiza usuario da base com campos não nulos fornecidos
            modelMapper.map(usuarioParaAtualizar, usuarioExistente);
            return usuarioExistente; // retorna usuario da base após atualização
        }

        throw new AccessDeniedException("Operação não permitida");

    }

    @Transactional
    public void excluirById(Integer id) {

        Usuario usuarioParaExcluir = usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        if (ehAdmin() || ehCadastroProprio(usuarioParaExcluir) ) {
            usuarioRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("Operação não permitida");
        }

    }

    private boolean ehCadastroProprio(Usuario usuarioConsultado) {

        Usuario usuarioLogado = usuarioRepository.findByUsername(getUsuarioLogado())
                .orElseThrow(NoSuchElementException::new);

        return usuarioLogado.getId().equals(usuarioConsultado.getId());

    }

    private boolean ehAdmin() {

        Usuario usuarioLogado = usuarioRepository.findByUsername(getUsuarioLogado())
                .orElseThrow(NoSuchElementException::new);

        return usuarioLogado.getAutenticacao().getRoles().contains(ROLE_MANAGERS);

    }

    private String getUsuarioLogado() {

        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

}
