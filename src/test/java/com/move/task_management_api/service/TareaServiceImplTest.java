package com.move.task_management_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.move.task_management_api.exception.CustomExceptions;
import com.move.task_management_api.model.EstadoTarea;
import com.move.task_management_api.model.Tarea;
import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.repository.ITareaRespository;
import com.move.task_management_api.service.impl.TareaServiceImpl;
import com.move.task_management_api.service.strategy.CrearTareaOperation;

public class TareaServiceImplTest {

    @Mock
    private ITareaRespository tareaRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private TareaServiceImpl tareaService;

    @Mock
    private CrearTareaOperation crearTareaOperation;

    private Tarea tarea;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        tarea = new Tarea();
        tarea.setId(UUID.randomUUID());
        tarea.setNombre("Tarea de prueba");
    }

    @Test
    public void testListarTareas() {
        List<Tarea> tareas = new ArrayList<>();
        tareas.add(new Tarea());
        when(tareaRepository.findAll()).thenReturn(tareas);

        List<Tarea> result = tareaService.listar();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testListarTareasVacia() {
        when(tareaRepository.findAll()).thenReturn(new ArrayList<>());
        when(messageSource.getMessage(any(), any(), any(Locale.class))).thenReturn("Error");

        CustomExceptions.CustomNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CustomExceptions.CustomNotFoundException.class, () -> {
                    tareaService.listar();
                });

        assertEquals("Error", exception.getMessage());
    }

    @Test
    public void testListarTareasPorUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());

        List<Tarea> tareas = new ArrayList<>();
        tareas.add(new Tarea());
        when(tareaRepository.findAllByUsuario(usuario)).thenReturn(tareas);

        List<Tarea> result = tareaService.listarPorUsuario(usuario);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testListarTareasPorUsuarioVacia() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());

        when(tareaRepository.findAllByUsuario(usuario)).thenReturn(new ArrayList<>());
        when(messageSource.getMessage(any(), any(), any(Locale.class))).thenReturn("Error");

        CustomExceptions.CustomNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CustomExceptions.CustomNotFoundException.class, () -> {
                    tareaService.listarPorUsuario(usuario);
                });

        assertEquals("Error", exception.getMessage());
    }

@Test
    public void testListarTareasPorEstado() {
        String estadoId = "1";

        EstadoTarea estadoTarea = new EstadoTarea();
        estadoTarea.setId(Integer.parseInt(estadoId)); 
        estadoTarea.setNombre("Estado de Ejemplo"); 
        estadoTarea.setDescripcion("Descripción del Estado de Ejemplo"); 

        Tarea tarea = new Tarea();
        tarea.setEstado(estadoTarea);

        List<Tarea> tareas = new ArrayList<>();
        tareas.add(tarea);

        when(tareaRepository.findAll()).thenReturn(tareas);

        List<Tarea> result = tareaService.listarPorEstado(estadoId);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testListarTareasPorEstadoVacia() {
        String estadoId = "1";

        when(tareaRepository.findAll()).thenReturn(new ArrayList<>());
        when(messageSource.getMessage(any(), any(), any(Locale.class))).thenReturn("Error");

        CustomExceptions.CustomNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CustomExceptions.CustomNotFoundException.class, () -> {
                    tareaService.listarPorEstado(estadoId);
                });

        assertEquals("Error", exception.getMessage());
    }

    //OPERACIONES DE TAREA

     @Test
    void testCrearTarea() {
        doNothing().when(crearTareaOperation).execute(tarea);

        tareaService.ejecutarOperacion(tarea, crearTareaOperation);

        verify(crearTareaOperation).execute(tarea);
    }

    @Test
    void testObtenerTareaPorIdNotFound() {
        UUID idTarea = UUID.randomUUID();
        String errorMessage = "Tarea no encontrada";
        when(messageSource.getMessage("error.not_found.tarea", null, LocaleContextHolder.getLocale())).thenReturn(errorMessage);
        when(tareaRepository.findById(idTarea)).thenReturn(Optional.empty());

        CustomExceptions.CustomNotFoundException exception = assertThrows(
            CustomExceptions.CustomNotFoundException.class, () -> {
                tareaService.obtenerPorId(idTarea);
            });

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testListarTareasPorEstadoInvalido() {
        String estadoId = "abc"; // Estado inválido no numérico
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> {
                tareaService.listarPorEstado(estadoId);
            });

        assertEquals("El estadoId debe ser numérico.", exception.getMessage());
    }

    @Test
    void testListarTareasPorEstadoNoEncontrado() {
        String estadoId = "1";
        String errorMessage = "No se encontraron tareas para el estado";
        when(messageSource.getMessage("error.not_found.tarea", null, LocaleContextHolder.getLocale())).thenReturn(errorMessage);
        when(tareaRepository.findAll()).thenReturn(new ArrayList<>());

        CustomExceptions.CustomNotFoundException exception = assertThrows(
            CustomExceptions.CustomNotFoundException.class, () -> {
                tareaService.listarPorEstado(estadoId);
            });

        assertEquals(errorMessage, exception.getMessage());
    }
}
