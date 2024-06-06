package com.move.task_management_api.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.move.task_management_api.exception.CustomExceptions;
import com.move.task_management_api.exception.CustomExceptions.CustomBadRequestException;
import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.repository.IUsuarioRepository;
import com.move.task_management_api.service.IUsuarioService;
import com.move.task_management_api.service.strategy.IPasswordEncoderOperation;
import com.move.task_management_api.service.strategy.ITokenOperation;
@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final IPasswordEncoderOperation passwordEncoderOperation;
    private final ITokenOperation tokenOperation;
    private final MessageSource messageSource;

    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository, IPasswordEncoderOperation passwordEncoderOperation,
                              ITokenOperation tokenOperation, MessageSource messageSource) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoderOperation = passwordEncoderOperation;
        this.tokenOperation = tokenOperation;
        this.messageSource = messageSource;
    }

    @Override
    public Usuario obtenerPorEmailYClave(String email, String clave) {
        String errorMessage = messageSource.getMessage("error.bad_request", null, LocaleContextHolder.getLocale());
        return usuarioRepository.findByEmail(email)
                .filter(user -> passwordEncoderOperation.matches(clave, user.getClave()))
                .map(this::generarTokenYActualizar)
                .orElseThrow(() -> new CustomExceptions.CustomBadRequestException(errorMessage));
    }

    @Override
    public Usuario obtenerPorId(UUID uuid) {
        String errorMessage = messageSource.getMessage("error.not_found.usuario", null, LocaleContextHolder.getLocale());

        return usuarioRepository.findById(uuid)
                                .orElseThrow(() -> new CustomExceptions.CustomNotFoundException(errorMessage));
    }

    @Override
    public List<Usuario> listar() {
        String errorMessage = messageSource.getMessage("error.not_found.usuario", null, LocaleContextHolder.getLocale());

        return Optional.ofNullable(usuarioRepository.findAll())
                        .filter(usuarios -> !usuarios.isEmpty())
                        .orElseThrow(() -> new CustomExceptions.CustomNotFoundException(errorMessage));
    }

    @Override
    @Transactional
    public Usuario crear(Usuario usuario) {
        usuarioRepository.findByEmail(usuario.getEmail())
                .ifPresent(existingUsuario -> {
                    throw new CustomExceptions.CustomUserAlreadyExistsException("El usuario con email " + usuario.getEmail() + " ya existe.");
                });

        usuario.setClave(passwordEncoderOperation.encode(usuario.getClave()));
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(UUID uuid) {
        String errorMessage = messageSource.getMessage("error.not_found.usuario", null, LocaleContextHolder.getLocale());

        if (!usuarioRepository.existsById(uuid)) {
            throw new CustomExceptions.CustomNotFoundException(errorMessage);
        }
        usuarioRepository.deleteById(uuid);
    }

    @Override
    @Transactional
    public Usuario actualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new CustomBadRequestException("Email NO encontrado"));
    }

    private Usuario generarTokenYActualizar(Usuario user) {
        String token = tokenOperation.generaToken(user);
        user.setToken(token);
        return actualizar(user);
    }
}
