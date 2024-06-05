package com.move.task_management_api.service.strategy;

import com.move.task_management_api.model.Tarea;

public interface ITareaOperation {
    void execute(Tarea tarea);
}

