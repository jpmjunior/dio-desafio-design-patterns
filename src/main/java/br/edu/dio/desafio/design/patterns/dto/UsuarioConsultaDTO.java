package br.edu.dio.desafio.design.patterns.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "UsuarioConsulta", description = "Dados de consulta de usuário")
public class UsuarioConsultaDTO {

    private Integer id;

    @Schema(example = "José Júnior")
    private String nome;

    private EnderecoDTO endereco;

    private AutenticacaoConsultaDTO autenticacao;

 }
