package br.edu.dio.desafio.design.patterns.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Sessão", description = "Dados de sessão do usuário")
public class Sessao {

    @Schema(description = "Nome de usuário", example = "josejunior")
    private String username;

    @Schema(description = "Token do usuário", example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3Mjg3NTgxMzUsImV4cCI6MTcyODc2MTczNSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSUyIsIlJPTEVfTUFOQUdFUlMiXX0.b8vnO0vuy6qSzBOs9s40SjqYq8vX_unVSHtuHJ3bbnrGBhJgRR_09JbUILBNBnayD9sxgCPyOwULmcqKfI2aRQ")
    private String token;

}
