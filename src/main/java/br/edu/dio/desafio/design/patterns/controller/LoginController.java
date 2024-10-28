package br.edu.dio.desafio.design.patterns.controller;

import br.edu.dio.desafio.design.patterns.security.Login;
import br.edu.dio.desafio.design.patterns.security.Sessao;
import br.edu.dio.desafio.design.patterns.security.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login de usuários")
@RestController
public class LoginController {

    private final AutenticacaoService autenticacaoService;

    public LoginController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

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
    public ResponseEntity<Sessao> logar(@RequestBody @Valid Login login){
        Sessao sessao = autenticacaoService.autenticarUsuario(login);
        return ResponseEntity.ok(sessao);

    }
}
