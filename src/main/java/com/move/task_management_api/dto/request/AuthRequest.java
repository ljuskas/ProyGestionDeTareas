package com.move.task_management_api.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String clave;
}
