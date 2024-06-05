package com.move.task_management_api.service.strategy;

import com.move.task_management_api.model.Usuario;

public interface ITokenOperation {
    String generaToken(Usuario usuario);
}

