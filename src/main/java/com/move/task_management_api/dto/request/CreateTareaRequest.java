package com.move.task_management_api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateTareaRequest {
    @NotBlank(message = "Debe ingresar el nombre de la tarea")
    @NotNull( message = "El nombre no debe ser nulo")
    private String              nombre;
    private String              descripcion;
    @NotNull( message = "El estado no debe ser nulo")
    private EstadoTareaRequest  estado;
}
