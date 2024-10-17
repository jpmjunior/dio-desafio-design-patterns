package br.edu.dio.desafio.design.patterns.controller;

import br.edu.dio.desafio.design.patterns.dto.Login;
import br.edu.dio.desafio.design.patterns.dto.Sessao;
import br.edu.dio.desafio.design.patterns.model.UserAuth;
import br.edu.dio.desafio.design.patterns.repository.UserAuthRepository;
import br.edu.dio.desafio.design.patterns.security.JWTCreator;
import br.edu.dio.desafio.design.patterns.security.JWTObject;
import br.edu.dio.desafio.design.patterns.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Tag(name = "Login de usuários")
@RestController
public class LoginController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Operation(
            summary = "Login",
            description = "Endpoint para login de usuário")
    @ApiResponse(
            responseCode = "200",
            description = "Login realizado com sucesso",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Sessao.class)))
    @PostMapping("/login")
    public ResponseEntity<Sessao> logar(@RequestBody Login login){
        UserAuth userAuth = userAuthRepository.findByUsername(login.getUsername());
        if(userAuth !=null) {
            boolean passwordOk = encoder.matches(login.getPassword(), userAuth.getPassword());
            if (!passwordOk) {
                throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
            }
            //Estamos enviando um objeto Sessão para retornar mais informações do usuário
            Sessao sessao = new Sessao();
            sessao.setUsername(userAuth.getUsername());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(userAuth.getRoles());
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
            return ResponseEntity.ok(sessao);
        }else {
            throw new RuntimeException("Erro ao tentar fazer login");
        }
    }

}
