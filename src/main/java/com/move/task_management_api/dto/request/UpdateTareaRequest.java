package com.move.task_management_api.dto.request;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateTareaRequest {
    @NotNull( message = "El UUID no debe ser nulo")
    private UUID                id;
    @NotNull( message = "El estado no debe ser nulo")
    private EstadoTareaRequest  estado;
    private String              comentarioModificacion;
}
