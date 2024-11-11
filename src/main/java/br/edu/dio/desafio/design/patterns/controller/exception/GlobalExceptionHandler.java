package br.edu.dio.desafio.design.patterns.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErroDTO> handleNotFoundException(NoSuchElementException notFoundException) {
        ErroDTO erroDTO = ErroDTO.builder().mensagem("Recurso não encontrado").build();
        return new ResponseEntity<>(erroDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroDTO> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        ErroDTO erroDTO = ErroDTO.builder().mensagem("Operação não permitida").build();
        return new ResponseEntity<>(erroDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroDTO> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        ErroDTO erroDTO = ErroDTO.builder().mensagem(illegalArgumentException.getMessage()).build();
        return new ResponseEntity<>(erroDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErroDTO> handleUnexpectedException(Throwable unexpectedException) {
        ErroDTO erroDTO = ErroDTO.builder().mensagem(unexpectedException.getMessage()).build();
        return new ResponseEntity<>(erroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
