package br.edu.dio.desafio.design.patterns.init;

import br.edu.dio.desafio.design.patterns.model.Endereco;
import br.edu.dio.desafio.design.patterns.model.Autenticacao;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import br.edu.dio.desafio.design.patterns.repository.UsuarioRepository;
import br.edu.dio.desafio.design.patterns.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class StartApplication implements CommandLineRunner {

    private final UsuarioRepository repository;
    private final UsuarioService service;

    public StartApplication(UsuarioRepository repository, UsuarioService service) {
        this.repository = repository;
        this.service = service;
    }

    @Transactional
    @Override
    public void run(String... args) {
        Optional<Usuario> usuario = repository.findByUsername("admin");
        if(usuario.isEmpty()){
            Usuario novoUsuario = Usuario.builder()
                    .nome("Admin")
                    .endereco(Endereco.builder().cep("65056000").build())
                    .autenticacao(Autenticacao.builder()
                            .username("admin")
                            .password("master123")
                            .roles(List.of("MANAGERS"))
                            .build())
                    .build();
            service.criar(novoUsuario);
        }
        usuario = repository.findByUsername("user");
        if(usuario.isEmpty()){
            Usuario novoUsuario = Usuario.builder()
                    .nome("User")
                    .endereco(Endereco.builder().cep("65056000").build())
                    .autenticacao(Autenticacao.builder()
                            .username("user")
                            .password("user123")
                            .roles(List.of("USERS"))
                            .build())
                    .build();
            service.criar(novoUsuario);
        }
    }

}
