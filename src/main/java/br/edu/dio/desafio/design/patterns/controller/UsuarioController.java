package br.edu.dio.desafio.design.patterns.controller;

import br.edu.dio.desafio.design.patterns.dto.UsuarioAtualizacaoDTO;
import br.edu.dio.desafio.design.patterns.dto.UsuarioCadastroDTO;
import br.edu.dio.desafio.design.patterns.dto.UsuarioConsultaDTO;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import br.edu.dio.desafio.design.patterns.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
            summary = "Cadastrar usuário",
            description = "Endpoint para realizar cadastro de usuários")
    @ApiResponse(
            responseCode = "201",
            description = "Cadastro realizado com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @PostMapping
    public ResponseEntity<UsuarioConsultaDTO> cadastrar(@RequestBody @Valid UsuarioCadastroDTO usuarioCadastroDTO, HttpServletRequest request){

        Usuario usuarioEntity = modelMapper.map(usuarioCadastroDTO, Usuario.class);
        service.cadastrar(usuarioEntity);
        UsuarioConsultaDTO usuarioCadastrado = modelMapper.map(usuarioEntity, UsuarioConsultaDTO.class);

        // construindo a URL completa do recurso criado
        String baseUrl = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
                .replacePath("/users/" + usuarioCadastrado.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(baseUrl)).body(usuarioCadastrado);
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
    @GetMapping("logged")
    public ResponseEntity<UsuarioConsultaDTO> consultarUsuarioLogado() {
        Usuario usuario = service.consultarUsuarioLogado();
        UsuarioConsultaDTO usuarioConsultaDTO = modelMapper.map(usuario, UsuarioConsultaDTO.class);
        return ResponseEntity.ok(usuarioConsultaDTO);
    }

    @SecurityRequirement(name = "tokenJWT")
    @Operation(
            summary = "Consultar todos os usuários",
            description = "Endpoint para consultar todos os usuários")
    @ApiResponse(
            responseCode = "200",
            description = "Consulta realizada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(
            responseCode = "403",
            description = "Não autorizado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping("all")
    public ResponseEntity<List<UsuarioConsultaDTO>> consultarTodos() {
        List<Usuario> usuarios = service.consultarTodos();
        List<UsuarioConsultaDTO> usuariosDTO = usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioConsultaDTO.class))
                .toList();
        return ResponseEntity.ok(usuariosDTO);
    }

    @SecurityRequirement(name = "tokenJWT")
    @Operation(
            summary = "Consultar usuário por ID",
            description = "Endpoint para consultar usuário por ID")
    @ApiResponse(
            responseCode = "200",
            description = "Consulta realizada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(
            responseCode = "403",
            description = "Não autorizado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(
            responseCode = "404",
            description = "Não encontrado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping("{id}")
    public ResponseEntity<UsuarioConsultaDTO> consultarById(@PathVariable Integer id) {
        Usuario usuario = service.consultarById(id);
        UsuarioConsultaDTO usuarioConsultaDTO = modelMapper.map(usuario, UsuarioConsultaDTO.class);
        return ResponseEntity.ok(usuarioConsultaDTO);
    }

    @SecurityRequirement(name = "tokenJWT")
    @Operation(
            summary = "Atualizar usuário",
            description = "Endpoint para realizar atualização de usuários")
    @ApiResponse(
            responseCode = "200",
            description = "Atualização realizada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @PutMapping
    public ResponseEntity<UsuarioConsultaDTO> atualizar(@RequestBody UsuarioAtualizacaoDTO usuarioParaAtualizar) {
        Usuario usuarioParaAtualizarEntity = modelMapper.map(usuarioParaAtualizar, Usuario.class);
        Usuario usuarioAtualizadoEntity = service.atualizar(usuarioParaAtualizarEntity);
        UsuarioConsultaDTO usuarioAtualizadoDTO = modelMapper.map(usuarioAtualizadoEntity, UsuarioConsultaDTO.class);
        return ResponseEntity.ok(usuarioAtualizadoDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluirById(@PathVariable Integer id) {
        service.excluirById(id);
        return ResponseEntity.ok().build();
    }

}
