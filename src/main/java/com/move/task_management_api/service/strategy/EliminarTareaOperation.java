package com.move.task_management_api.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.move.task_management_api.model.Tarea;
import com.move.task_management_api.repository.ITareaRespository;

@Component
public class EliminarTareaOperation implements ITareaOperation {

    @Autowired
    private ITareaRespository tareaRepository;

    @Override
    public void execute(Tarea tarea) {
        tareaRepository.delete(tarea);
    }
}
