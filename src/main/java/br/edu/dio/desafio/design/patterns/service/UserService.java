package br.edu.dio.desafio.design.patterns.service;

import br.edu.dio.desafio.design.patterns.dto.EnderecoDTO;
import br.edu.dio.desafio.design.patterns.external.facade.ViaCepClient;
import br.edu.dio.desafio.design.patterns.model.Endereco;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import br.edu.dio.desafio.design.patterns.repository.UserAuthRepository;
import br.edu.dio.desafio.design.patterns.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private ViaCepClient viaCepClient;
    private UsuarioRepository usuarioRepository;
    private UserAuthRepository userAuthRepository;
    private PasswordEncoder encoder;

    public UserService(ViaCepClient viaCepClient, UsuarioRepository usuarioRepository, UserAuthRepository userAuthRepository, PasswordEncoder encoder) {
        this.viaCepClient = viaCepClient;
        this.usuarioRepository = usuarioRepository;
        this.userAuthRepository = userAuthRepository;
        this.encoder = encoder;
    }

    public void createUser(Usuario usuario){
        String pass = usuario.getAuth().getPassword();

        //criptografando antes de salvar no banco
        usuario.getAuth().setPassword(encoder.encode(pass));

        //consultando CEP na API ViaCEP
        EnderecoDTO enderecoDTO = viaCepClient.consultarCep(usuario.getEndereco().getCep());
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

        userAuthRepository.save(usuario.getAuth());
        usuarioRepository.save(usuario);
    }

}
