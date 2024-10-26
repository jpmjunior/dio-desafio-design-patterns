package br.edu.dio.desafio.design.patterns.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AutenticacaoConsulta", description = "Dados de consulta de autenticação")
public class AutenticacaoConsultaDTO {

    @Schema(description = "Nome de usuário", example = "josejunior")
    private String username;

    @Schema(description = "Perfis de acesso", example = "[\"USERS\",\"MANAGERS\"]")
    private List<String> roles;

}
