package br.edu.dio.desafio.design.patterns.init;

import br.edu.dio.desafio.design.patterns.model.Endereco;
import br.edu.dio.desafio.design.patterns.model.UserAuth;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import br.edu.dio.desafio.design.patterns.repository.UsuarioRepository;
import br.edu.dio.desafio.design.patterns.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class StartApplication implements CommandLineRunner {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UserService service;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Usuario usuario = repository.findByUsername("admin");
        if(usuario ==null){
            usuario = Usuario.builder()
                    .nome("Admin")
                    .endereco(Endereco.builder().cep("65056000").build())
                    .auth(UserAuth.builder()
                            .username("admin")
                            .password("master123")
                            .roles(List.of("MANAGERS"))
                            .build())
                    .build();
            service.createUser(usuario);
        }
        usuario = repository.findByUsername("user");
        if(usuario ==null){
            usuario = Usuario.builder()
                    .nome("User")
                    .endereco(Endereco.builder().cep("65056000").build())
                    .auth(UserAuth.builder()
                            .username("user")
                            .password("user123")
                            .roles(List.of("USERS"))
                            .build())
                    .build();
            service.createUser(usuario);
        }
    }

}
