package com.move.task_management_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.move.task_management_api.dto.UsuarioDto;
import com.move.task_management_api.dto.request.CreateUserRequest;
import com.move.task_management_api.model.Usuario;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tareas", ignore = true)
    @Mapping(target = "token", ignore = true)
    Usuario             toEntity(CreateUserRequest createUserRequest);
    UsuarioDto          toDto(Usuario usuario);
    List<UsuarioDto>    toDtoList(List<Usuario> usuarios);
}
