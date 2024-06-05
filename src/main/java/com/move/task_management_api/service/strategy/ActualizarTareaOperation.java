package com.move.task_management_api.service.strategy;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.move.task_management_api.model.Tarea;
import com.move.task_management_api.repository.ITareaRespository;
import com.move.task_management_api.service.ITareaService;

@Component
public class ActualizarTareaOperation implements ITareaOperation {

    @Autowired
    private ITareaRespository tareaRepository;

    @Autowired
    private ITareaService tareaService;

    @Override
    public void execute(Tarea tarea) {
        ZonedDateTime fechaActual = ZonedDateTime.now();
        Tarea objTarea = tareaService.obtenerPorId(tarea.getId());
        objTarea.setFechaModificacion(fechaActual);
        objTarea.setUsuarioModificador(tarea.getUsuarioModificador());
        objTarea.setEstado(tarea.getEstado());
        objTarea.setComentarioModificacion(tarea.getComentarioModificacion());
        tareaRepository.save(objTarea);
    }

    public Tarea actualizar(Tarea tarea) {
        execute(tarea);
        return tareaService.obtenerPorId(tarea.getId());
    }
}