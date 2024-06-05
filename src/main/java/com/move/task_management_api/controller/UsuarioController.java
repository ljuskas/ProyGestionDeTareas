package com.move.task_management_api.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.move.task_management_api.dto.UsuarioDto;
import com.move.task_management_api.dto.request.CreateUserRequest;
import com.move.task_management_api.dto.response.ErrorResponse;
import com.move.task_management_api.mapper.UsuarioMapper;
import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.service.IUsuarioService;
import com.move.task_management_api.util.swagger.ErrorResponseExamples;
import com.move.task_management_api.validation.ValidUUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado con éxito", content = @Content(schema = @Schema(implementation = UsuarioDto.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_400))),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_401))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_500)))
    })
    @PostMapping("/public/usuario/crear")
    public ResponseEntity<UsuarioDto> crear(
        @Valid @RequestBody 
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Detalles del usuario a crear", required = true, 
                    content = @Content(schema = @Schema(implementation = CreateUserRequest.class)))
        CreateUserRequest createUserRequest) {

        Usuario usuario = usuarioService.crear(UsuarioMapper.INSTANCE.toEntity(createUserRequest));
        return ResponseEntity.ok(UsuarioMapper.INSTANCE.toDto(usuario));
    }

    @Operation(summary = "Obtener un usuario por su ID", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Data View", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema= @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "DEFAULT", description = "Default error.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))) 
    })
    @GetMapping("/private/usuario/{id}")
    public ResponseEntity<UsuarioDto> obternerPorId(@ValidUUID @PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        Usuario usuario = usuarioService.obtenerPorId(uuid);
        return ResponseEntity.ok(UsuarioMapper.INSTANCE.toDto(usuario));
    }

    @Operation(summary = "Listar todos los usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema= @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "DEFAULT", description = "Default error.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))) 
    })
    @GetMapping("/public/usuario/all")
    public ResponseEntity<List<UsuarioDto>> listar() {
        List<UsuarioDto> usuarios = UsuarioMapper.INSTANCE.toDtoList(usuarioService.listar());
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Eliminar un usuario por su ID", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado satisfactoriamente.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema= @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "DEFAULT", description = "Default error.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))) 
    })
    @DeleteMapping("/private/usuario/delete/{id}")
    public ResponseEntity<String> eliminarPorId(@ValidUUID @PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        usuarioService.eliminar(uuid);
        return ResponseEntity.ok("Usuario eliminado satisfactoriamente.");
    }
}
