package com.move.task_management_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.move.task_management_api.exception.CustomExceptions;
import com.move.task_management_api.model.EstadoTarea;
import com.move.task_management_api.repository.IEstadoTareaRepository;
import com.move.task_management_api.service.IEstadoTareaService;

@Service
public class EstadoTareaServiceImpl implements IEstadoTareaService {

    @Autowired
    IEstadoTareaRepository estadoTareaRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public List<EstadoTarea> listar() {
        String errorMessage = messageSource.getMessage("error.not_found.estado", null, LocaleContextHolder.getLocale());

        return Optional.ofNullable(estadoTareaRepository.findAll())
                     .filter(list -> !list.isEmpty())
                     .orElseThrow(()-> new CustomExceptions.CustomNotFoundException(errorMessage));
    }

}
