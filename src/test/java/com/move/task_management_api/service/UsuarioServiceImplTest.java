package com.move.task_management_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import com.move.task_management_api.exception.CustomExceptions;
import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.repository.IUsuarioRepository;
import com.move.task_management_api.service.impl.UsuarioServiceImpl;
import com.move.task_management_api.service.strategy.IPasswordEncoderOperation;
import com.move.task_management_api.service.strategy.ITokenOperation;

public class UsuarioServiceImplTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private IPasswordEncoderOperation passwordEncoderOperation;

    @Mock
    private ITokenOperation tokenOperation;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;


    private Usuario usuario;
    private String email = "test@example.com";
    private String clave = "password";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setClave(clave);
    }

    @Test
    public void testObtenerPorEmailYClaveBadRequest() {
    
        usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setClave(clave);
    
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        when(passwordEncoderOperation.matches(clave, usuario.getClave())).thenReturn(true);
        when(tokenOperation.generaToken(usuario)).thenReturn("token");
    
        assertThrows(CustomExceptions.CustomBadRequestException.class, () -> {
            usuarioService.obtenerPorEmailYClave(email, clave);
        });
    }
    

    @Test
    public void testObtenerPorId() {
        UUID uuid = UUID.randomUUID();
        usuario = new Usuario();
        usuario.setId(uuid);

        when(usuarioRepository.findById(uuid)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.obtenerPorId(uuid);

        assertNotNull(result);
        assertEquals(uuid, result.getId());
    }

    @Test
    public void testListar() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario());

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> result = usuarioService.listar();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testCrearUsuario() {
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoderOperation.encode(clave)).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario creado = usuarioService.crear(usuario);

        verify(usuarioRepository).findByEmail(email);
        verify(passwordEncoderOperation).encode(clave);
        verify(usuarioRepository).save(usuario);

        assertNotNull(creado);
        assertEquals(email, creado.getEmail());
        assertEquals("encodedPassword", creado.getClave());
    }

    @Test
    void testCrearUsuarioYaExiste() {
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        Exception exception = assertThrows(CustomExceptions.CustomUserAlreadyExistsException.class, () -> {
            usuarioService.crear(usuario);
        });

        assertEquals("El usuario con email " + email + " ya existe.", exception.getMessage());

        verify(usuarioRepository).findByEmail(email);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    //Falta cubrir, pero por honor al tiempo se dejan los básicos
}
