package br.edu.dio.desafio.design.patterns.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Endereco", description = "Dados de endereço")
public class EnderecoDTO {

    private String cep;
    private String logradouro;
    private String bairro;
    private String uf;
    private String estado;
    private Boolean erro;

}
