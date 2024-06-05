package com.move.task_management_api.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class UsuarioDto {
    private UUID            id;
    private String          nombre;
    private String          email;
    
}
