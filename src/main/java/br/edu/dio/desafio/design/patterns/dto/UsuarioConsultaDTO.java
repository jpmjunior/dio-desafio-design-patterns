package br.edu.dio.desafio.design.patterns.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UsuarioConsulta", description = "Dados de consulta de usuário")
public class UsuarioConsultaDTO {

    @Schema(example = "José Júnior")
    private String nome;

    private EnderecoDTO endereco;

    private AutenticacaoConsultaDTO autenticacao;

 }
