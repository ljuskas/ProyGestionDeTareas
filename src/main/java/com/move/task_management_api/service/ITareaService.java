package com.move.task_management_api.service;

import java.util.List;
import java.util.UUID;

import com.move.task_management_api.model.Tarea;
import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.service.strategy.ITareaOperation;

public interface ITareaService {
    void        ejecutarOperacion(Tarea tarea, ITareaOperation operacion);
    Tarea       obtenerPorId(UUID tareaId);
    List<Tarea> listar();
    List<Tarea> listarPorUsuario(Usuario usuario);
    List<Tarea> listarPorEstado(String estadoId);
}

