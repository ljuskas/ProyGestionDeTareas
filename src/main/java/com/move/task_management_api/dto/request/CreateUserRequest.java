package com.move.task_management_api.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String nombre;
    @NotBlank(message = "Debe ingresar Email")
    @NotNull(message = "El email no puede ser nulo")
    @Email(message = "Debe proporcionar un email válido")
    private String email;
    @NotBlank(message = "Debe ingresar clave")
    @NotNull(message = "La clave no puede ser nula")
    @Size(min = 8, max = 12)
    private String clave;
}
