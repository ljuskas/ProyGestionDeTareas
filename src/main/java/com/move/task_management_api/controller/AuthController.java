package com.move.task_management_api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.move.task_management_api.dto.UsuarioDto;
import com.move.task_management_api.dto.request.AuthRequest;
import com.move.task_management_api.dto.response.AuthResponse;
import com.move.task_management_api.dto.response.ErrorResponse;
import com.move.task_management_api.exception.CustomExceptions;
import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.service.IUsuarioService;
import com.move.task_management_api.util.swagger.ErrorResponseExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticación", description = "Operaciones relacionadas con la autenticación")
@RestController
@RequestMapping("/api/public/auth")
public class AuthController {

    @Autowired
    private IUsuarioService usuarioService;

    @Operation(summary = "Iniciar sesión")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado con éxito", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_400_USER)))
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Validated @RequestBody AuthRequest request) {
        Usuario objUsuario = usuarioService.obtenerPorEmailYClave(request.getEmail(), request.getClave());
        return ResponseEntity.ok(new AuthResponse(objUsuario.getToken()));
    }

    @Operation(summary = "Manejo de errores de autenticación")
    @GetMapping("/error")
    public ResponseEntity<?> error(HttpServletRequest request) {
        String errorMessage = (String) request.getSession().getAttribute("error");
        throw new CustomExceptions.CustomAccessDeniedException(errorMessage);
    }
}