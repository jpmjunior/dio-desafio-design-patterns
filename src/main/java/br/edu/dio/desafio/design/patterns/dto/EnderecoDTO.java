package br.edu.dio.desafio.design.patterns.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Endereco", description = "Dados de endereço")
public class EnderecoDTO {

    @NotBlank
    private String cep;

    private String logradouro;

    private String bairro;

    private String uf;

    private String estado;

    private Boolean erro;

}
