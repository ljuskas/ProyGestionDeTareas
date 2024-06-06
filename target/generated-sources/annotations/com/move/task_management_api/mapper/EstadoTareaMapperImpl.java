package com.move.task_management_api.mapper;

import com.move.task_management_api.dto.EstadoTareaDto;
import com.move.task_management_api.dto.request.EstadoTareaRequest;
import com.move.task_management_api.model.EstadoTarea;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-05T22:07:28-0400",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240524-2033, environment: Java 17.0.11 (Eclipse Adoptium)"
)
public class EstadoTareaMapperImpl implements EstadoTareaMapper {

    @Override
    public List<EstadoTareaDto> toListDto(List<EstadoTarea> estadosTarea) {
        if ( estadosTarea == null ) {
            return null;
        }

        List<EstadoTareaDto> list = new ArrayList<EstadoTareaDto>( estadosTarea.size() );
        for ( EstadoTarea estadoTarea : estadosTarea ) {
            list.add( estadoTareaToEstadoTareaDto( estadoTarea ) );
        }

        return list;
    }

    @Override
    public EstadoTarea toEntity(EstadoTareaDto estadoTarea) {
        if ( estadoTarea == null ) {
            return null;
        }

        EstadoTarea estadoTarea1 = new EstadoTarea();

        estadoTarea1.setDescripcion( estadoTarea.getDescripcion() );
        estadoTarea1.setId( estadoTarea.getId() );
        estadoTarea1.setNombre( estadoTarea.getNombre() );

        return estadoTarea1;
    }

    @Override
    public EstadoTarea toEntity(EstadoTareaRequest estadoTareaRequest) {
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
