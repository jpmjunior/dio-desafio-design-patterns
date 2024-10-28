package br.edu.dio.desafio.design.patterns.controller;

import br.edu.dio.desafio.design.patterns.dto.UsuarioCadastroDTO;
import br.edu.dio.desafio.design.patterns.dto.UsuarioConsultaDTO;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import br.edu.dio.desafio.design.patterns.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("users")
@Tag(name = "Gestão de usuários", description = "Operações relacionadas aos usuários")
public class UsuarioController {

    private final UsuarioService service;
    private final ModelMapper modelMapper;

    public UsuarioController(UsuarioService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @Operation(
            summary = "Cadastrar de usuário",
            description = "Endpoint para realizar cadastro de usuários")
    @ApiResponse(
            responseCode = "201",
            description = "Cadastro realizado com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @PostMapping
    public ResponseEntity<UsuarioConsultaDTO> criar(@RequestBody @Valid UsuarioCadastroDTO usuarioCadastroDTO){
        Usuario usuarioEntity = modelMapper.map(usuarioCadastroDTO, Usuario.class);
        service.criar(usuarioEntity);
        UsuarioConsultaDTO usuarioCadastrado = modelMapper.map(usuarioEntity, UsuarioConsultaDTO.class);
        return ResponseEntity.created(URI.create("/users")).body(usuarioCadastrado);
    }

    @SecurityRequirement(name = "tokenJWT")
    @Operation(
            summary = "Consultar usuário logado",
            description = "Endpoint para consultar usuário logado")
    @ApiResponse(
            responseCode = "200",
            description = "Consulta realizada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(
            responseCode = "403",
            description = "Não autorizado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping
    public ResponseEntity<UsuarioConsultaDTO> consultarUsuarioLogado() {
        Usuario usuario = service.consultarUsuarioLogado();
        UsuarioConsultaDTO usuarioConsultaDTO = modelMapper.map(usuario, UsuarioConsultaDTO.class);
        return ResponseEntity.ok(usuarioConsultaDTO);
    }

}
