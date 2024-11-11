package br.edu.dio.desafio.design.patterns.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErroDTO {

    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();
    private String mensagem;

}
