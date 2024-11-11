package br.edu.dio.desafio.design.patterns.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AutenticacaoAtualizacao", description = "Dados de atualização de autenticação")
public class AutenticacaoAtualizacaoDTO {

    @NotNull
    @Schema(example = "3")
    private Integer id;

    @NotBlank
    @Schema(description = "Nome de usuário", example = "josejunior")
    private String username;

    @NotBlank
    @Schema(description = "Senha do usuário", example = "senha1234")
    private String password;

    @Schema(description = "Perfis de acesso", example = "[\"USERS\",\"MANAGERS\"]")
    private List<String> roles;

}