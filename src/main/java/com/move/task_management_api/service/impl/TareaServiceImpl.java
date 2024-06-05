package com.move.task_management_api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.move.task_management_api.exception.CustomExceptions;
import com.move.task_management_api.model.Tarea;
import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.repository.ITareaRespository;
import com.move.task_management_api.service.ITareaService;
import com.move.task_management_api.service.strategy.ITareaOperation;

@Service
public class TareaServiceImpl implements ITareaService {

    @Autowired
    private ITareaRespository tareaRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public void ejecutarOperacion(Tarea tarea, ITareaOperation operacion) {
        operacion.execute(tarea);
    }

    @Override
    public Tarea obtenerPorId(UUID idTarea) {
        String errorMessage = messageSource.getMessage("error.not_found.tarea", null, LocaleContextHolder.getLocale());
        return tareaRepository.findById(idTarea)
                              .orElseThrow(() -> new CustomExceptions.CustomNotFoundException(errorMessage));
    }

    @Override
    public List<Tarea> listar() {
        String errorMessage = messageSource.getMessage("error.not_found.tarea", null, LocaleContextHolder.getLocale());
        return Optional.ofNullable(tareaRepository.findAll())
                       .filter(tareas -> !tareas.isEmpty())
                       .orElseThrow(() -> new CustomExceptions.CustomNotFoundException(errorMessage));
    }

    @Override
    public List<Tarea> listarPorUsuario(Usuario usuario) {
        String errorMessage = messageSource.getMessage("error.not_found.tarea", null, LocaleContextHolder.getLocale());
        return Optional.ofNullable(tareaRepository.findAllByUsuario(usuario))
                       .filter(tareas -> !tareas.isEmpty())
                       .orElseThrow(() -> new CustomExceptions.CustomNotFoundException(errorMessage));
    }

    @Override
    public List<Tarea> listarPorEstado(Integer estadoId) {
        String errorMessage = messageSource.getMessage("error.not_found.tarea", null, LocaleContextHolder.getLocale());
        List<Tarea> tareas = tareaRepository.findAll().stream()
                                             .filter(tarea -> Objects.nonNull(tarea.getEstado()) && tarea.getEstado().getId().equals(estadoId))
                                             .collect(Collectors.toList());
        if (tareas.isEmpty()) {
            throw new CustomExceptions.CustomNotFoundException(errorMessage);
        }
        return tareas;
    }
}
