package com.move.task_management_api.mapper;

import com.move.task_management_api.dto.UsuarioDto;
import com.move.task_management_api.dto.request.CreateUserRequest;
import com.move.task_management_api.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-05T22:07:28-0400",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240524-2033, environment: Java 17.0.11 (Eclipse Adoptium)"
)
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(CreateUserRequest createUserRequest) {
        if ( createUserRequest == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setClave( createUserRequest.getClave() );
        usuario.setEmail( createUserRequest.getEmail() );
        usuario.setNombre( createUserRequest.getNombre() );

        return usuario;
    }

    @Override
    public UsuarioDto toDto(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setEmail( usuario.getEmail() );
        usuarioDto.setId( usuario.getId() );
        usuarioDto.setNombre( usuario.getNombre() );

        return usuarioDto;
    }

    @Override
    public List<UsuarioDto> toDtoList(List<Usuario> usuarios) {
        if ( usuarios == null ) {
            return null;
        }

        List<UsuarioDto> list = new ArrayList<UsuarioDto>( usuarios.size() );
        for ( Usuario usuario : usuarios ) {
            list.add( toDto( usuario ) );
        }

        return list;
    }
}
