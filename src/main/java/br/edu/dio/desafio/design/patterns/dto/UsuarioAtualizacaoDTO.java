package br.edu.dio.desafio.design.patterns.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UsuarioAtualizacao", description = "Dados de atualizacao de usuário")
public class UsuarioAtualizacaoDTO {

    @NotNull
    @Schema(example = "3")
    private Integer id;

    @NotBlank
    @Schema(example = "José Júnior")
    private String nome;

    @NotBlank
    @Schema(example = "65056000")
    private String cep;

    @Schema(example = "20A")
    private String numero;

    @Schema(example = "Bloco A, Apt. 103")
    private String complemento;

}
