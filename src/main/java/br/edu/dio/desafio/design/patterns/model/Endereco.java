package br.edu.dio.desafio.design.patterns.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Endereco {

    private String cep;
    private String logradouro;
    private String bairro;
    private String uf;
    private String estado;
    private String numero;
    private String complemento;

}
