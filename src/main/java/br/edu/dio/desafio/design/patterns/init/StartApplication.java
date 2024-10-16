package br.edu.dio.desafio.design.patterns.init;

import br.edu.dio.desafio.design.patterns.model.User;
import br.edu.dio.desafio.design.patterns.repository.UserRepository;
import br.edu.dio.desafio.design.patterns.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class StartApplication implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User user = repository.findByUsername("admin");
        if(user==null){
            user = new User();
            user.setName("ADMIN");
            user.setUsername("admin");
            user.setPassword("master123");
            user.getRoles().add("MANAGERS");
            service.createUser(user);
        }
        user = repository.findByUsername("user");
        if(user ==null){
            user = new User();
            user.setName("USER");
            user.setUsername("user");
            user.setPassword("user123");
            user.getRoles().add("USERS");
            service.createUser(user);
        }
    }

}
