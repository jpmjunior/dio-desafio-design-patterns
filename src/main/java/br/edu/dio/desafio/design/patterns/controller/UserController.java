package br.edu.dio.desafio.design.patterns.controller;

import br.edu.dio.desafio.design.patterns.dto.UserDTO;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import br.edu.dio.desafio.design.patterns.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
@Tag(name = "Gestão de usuários", description = "Operações relacionadas aos usuários")
public class UserController {

    private UserService service;
    private ModelMapper modelMapper;

    public UserController(UserService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @Operation(
            summary = "Cadastro de usuários",
            description = "Endpoint para cadastro de usuários")
    @ApiResponse(
            responseCode = "201",
            description = "Cadastro realizado com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @PostMapping
    public ResponseEntity<UserDTO> postUser(@RequestBody UserDTO userDTO){
        Usuario usuarioEntity = modelMapper.map(userDTO, Usuario.class);
        service.createUser(usuarioEntity);
        return ResponseEntity.created(URI.create("/users")).build();
    }

}
