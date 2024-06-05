package com.move.task_management_api.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EstadoTareaRequest {
    @NotNull(message = "El id del estado no debe ser nulo")
    private Integer  id;
}
