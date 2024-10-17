package br.edu.dio.desafio.design.patterns.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Auth", description = "Dados de autenticação")
public class AuthDTO {

    @Schema(description = "Nome de usuário", example = "josejunior")
    private String username;

    @Schema(description = "Senha do usuário", example = "senha1234")
    private String password;

    @Schema(description = "Perfis de acesso", example = "[\"USERS\",\"MANAGERS\"]")
    private List<String> roles;

}
