package com.move.task_management_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.move.task_management_api.dto.EstadoTareaDto;
import com.move.task_management_api.dto.request.EstadoTareaRequest;
import com.move.task_management_api.model.EstadoTarea;

@Mapper
public interface EstadoTareaMapper {
    EstadoTareaMapper INSTANCE = Mappers.getMapper(EstadoTareaMapper.class);
    List<EstadoTareaDto> toListDto(List<EstadoTarea> estadosTarea);
    EstadoTarea toEntity(EstadoTareaDto estadoTarea);
    @Mapping(target = "nombre", ignore = true)
    @Mapping(target = "descripcion", ignore = true)
    EstadoTarea toEntity(EstadoTareaRequest estadoTareaRequest);

}
