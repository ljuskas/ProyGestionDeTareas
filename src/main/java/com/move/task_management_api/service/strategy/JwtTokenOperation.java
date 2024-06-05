package com.move.task_management_api.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.security.JwtTokenProvider;

@Service
public class JwtTokenOperation implements ITokenOperation {
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String generaToken(Usuario usuario) {
        return jwtTokenProvider.generaToken(usuario);
    }
}
