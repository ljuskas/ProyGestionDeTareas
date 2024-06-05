package com.move.task_management_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.move.task_management_api.dto.TareaDto;
import com.move.task_management_api.dto.request.CreateTareaRequest;
import com.move.task_management_api.dto.request.UpdateTareaRequest;
import com.move.task_management_api.model.Tarea;

@Mapper
public interface TareaMapper {
    TareaMapper INSTANCE = Mappers.getMapper(TareaMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "usuarioModificador", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "comentarioModificacion", ignore = true)
    Tarea           toEntity(CreateTareaRequest createTareaRequest);

    @Mapping(target = "descripcion", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "nombre", ignore = true)
    @Mapping(target = "usuarioModificador", ignore = true)
    Tarea           toEntity(UpdateTareaRequest updateTareaRequest);
    TareaDto        toDto(Tarea tarea);
    List<TareaDto>  toDtoList(List<Tarea> tareas);
}