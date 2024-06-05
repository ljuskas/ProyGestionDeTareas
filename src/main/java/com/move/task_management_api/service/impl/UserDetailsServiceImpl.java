package com.move.task_management_api.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.move.task_management_api.exception.CustomExceptions.CustomBadRequestException;
import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.repository.IUsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${spring.security.user.roles}")
    private String userRole;

    private final IUsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new CustomBadRequestException("Email NO encontrado"));

        return org.springframework.security.core.userdetails.User.builder()
            .username(usuario.getEmail())
            .password(usuario.getClave())
            .roles(userRole)
            .build();
    }
}
