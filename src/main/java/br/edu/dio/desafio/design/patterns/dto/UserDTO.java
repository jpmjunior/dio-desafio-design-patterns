package br.edu.dio.desafio.design.patterns.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User", description = "Dados de usuário")
public class UserDTO {

    @Schema(example = "José Júnior")
    private String nome;

    @Schema(example = "65555000")
    private String cep;

    @Schema(example = "18A")
    private String numero;

    @Schema(example = "Bloco A, Apt. 103")
    private String complemento;

    private AuthDTO auth;

 }
