package br.edu.dio.desafio.design.patterns.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Login", description = "Dados de login do usuário")
public class Login {

    @NotBlank
    @Schema(description = "Nome de usuário", example = "josejunior")
    private String username;

    @NotBlank
    @Schema(description = "Senha", example = "senha1234")
    private String password;

}
