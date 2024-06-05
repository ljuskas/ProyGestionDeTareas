package com.move.task_management_api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.move.task_management_api.dto.request.AuthRequest;
import com.move.task_management_api.dto.response.AuthResponse;
import com.move.task_management_api.exception.CustomExceptions;
import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.service.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticación", description = "Operaciones relacionadas con la autenticación")
@RestController
@RequestMapping("/api/public/auth")
public class AuthController {

    @Autowired
    private IUsuarioService usuarioService;

    @Operation(summary = "Iniciar sesión")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
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
