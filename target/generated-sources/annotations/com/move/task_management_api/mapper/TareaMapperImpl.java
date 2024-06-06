package com.move.task_management_api.mapper;

import com.move.task_management_api.dto.EstadoTareaDto;
import com.move.task_management_api.dto.TareaDto;
import com.move.task_management_api.dto.request.CreateTareaRequest;
import com.move.task_management_api.dto.request.EstadoTareaRequest;
import com.move.task_management_api.dto.request.UpdateTareaRequest;
import com.move.task_management_api.model.EstadoTarea;
import com.move.task_management_api.model.Tarea;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-05T22:07:28-0400",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240524-2033, environment: Java 17.0.11 (Eclipse Adoptium)"
)
public class TareaMapperImpl implements TareaMapper {

    @Override
    public Tarea toEntity(CreateTareaRequest createTareaRequest) {
        if ( createTareaRequest == null ) {
            return null;
        }

        Tarea tarea = new Tarea();

        tarea.setDescripcion( createTareaRequest.getDescripcion() );
        tarea.setEstado( estadoTareaRequestToEstadoTarea( createTareaRequest.getEstado() ) );
        tarea.setNombre( createTareaRequest.getNombre() );

        return tarea;
    }

    @Override
    public Tarea toEntity(UpdateTareaRequest updateTareaRequest) {
        if ( updateTareaRequest == null ) {
            return null;
        }

        Tarea tarea = new Tarea();

        tarea.setComentarioModificacion( updateTareaRequest.getComentarioModificacion() );
        tarea.setEstado( estadoTareaRequestToEstadoTarea( updateTareaRequest.getEstado() ) );
        tarea.setId( updateTareaRequest.getId() );

        return tarea;
    }

    @Override
    public TareaDto toDto(Tarea tarea) {
        if ( tarea == null ) {
            return null;
        }

        TareaDto tareaDto = new TareaDto();

        tareaDto.setComentarioModificacion( tarea.getComentarioModificacion() );
        tareaDto.setDescripcion( tarea.getDescripcion() );
        tareaDto.setEstado( estadoTareaToEstadoTareaDto( tarea.getEstado() ) );
        tareaDto.setFechaCreacion( tarea.getFechaCreacion() );
        tareaDto.setFechaModificacion( tarea.getFechaModificacion() );
        tareaDto.setId( tarea.getId() );
        tareaDto.setNombre( tarea.getNombre() );
        tareaDto.setUsuarioModificador( tarea.getUsuarioModificador() );

        return tareaDto;
    }

    @Override
    public List<TareaDto> toDtoList(List<Tarea> tareas) {
        if ( tareas == null ) {
            return null;
        }

        List<TareaDto> list = new ArrayList<TareaDto>( tareas.size() );
        for ( Tarea tarea : tareas ) {
            list.add( toDto( tarea ) );
        }

        return list;
    }

    protected EstadoTarea estadoTareaRequestToEstadoTarea(EstadoTareaRequest estadoTareaRequest) {
        if ( estadoTareaRequest == null ) {
            return null;
        }

        EstadoTarea estadoTarea = new EstadoTarea();

        estadoTarea.setId( estadoTareaRequest.getId() );

        return estadoTarea;
    }

    protected EstadoTareaDto estadoTareaToEstadoTareaDto(EstadoTarea estadoTarea) {
        if ( estadoTarea == null ) {
            return null;
        }

        EstadoTareaDto estadoTareaDto = new EstadoTareaDto();

        estadoTareaDto.setDescripcion( estadoTarea.getDescripcion() );
        estadoTareaDto.setId( estadoTarea.getId() );
        estadoTareaDto.setNombre( estadoTarea.getNombre() );

        return estadoTareaDto;
    }
}
