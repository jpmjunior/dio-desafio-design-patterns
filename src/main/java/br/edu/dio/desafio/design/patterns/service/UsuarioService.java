package br.edu.dio.desafio.design.patterns.service;

import br.edu.dio.desafio.design.patterns.dto.EnderecoDTO;
import br.edu.dio.desafio.design.patterns.external.facade.ViaCepClient;
import br.edu.dio.desafio.design.patterns.model.Endereco;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import br.edu.dio.desafio.design.patterns.repository.AutenticacaoRepository;
import br.edu.dio.desafio.design.patterns.repository.UsuarioRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuarioService {

    private final ViaCepClient viaCepClient;
    private final UsuarioRepository usuarioRepository;
    private final AutenticacaoRepository autenticacaoRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(ViaCepClient viaCepClient, UsuarioRepository usuarioRepository, AutenticacaoRepository autenticacaoRepository, PasswordEncoder encoder) {
        this.viaCepClient = viaCepClient;
        this.usuarioRepository = usuarioRepository;
        this.autenticacaoRepository = autenticacaoRepository;
        this.encoder = encoder;
    }

    public void criar(Usuario usuario){
        String pass = usuario.getAutenticacao().getPassword();

        //criptografando antes de salvar no banco
        usuario.getAutenticacao().setPassword(encoder.encode(pass));

        //consultando CEP na API ViaCEP
        try {
            EnderecoDTO enderecoDTO = viaCepClient.consultarCep(usuario.getEndereco().getCep());

            if (enderecoDTO == null || enderecoDTO.getErro()) {
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

}
