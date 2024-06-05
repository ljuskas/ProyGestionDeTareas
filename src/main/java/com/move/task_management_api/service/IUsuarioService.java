package com.move.task_management_api.service;

import java.util.List;
import java.util.UUID;

import com.move.task_management_api.model.Usuario;

public interface IUsuarioService {
    Usuario          obtenerPorEmailYClave(String email, String clave);
    Usuario          obtenerPorId(UUID id);
    Usuario          obtenerPorEmail(String email);
    List<Usuario>    listar();
    Usuario          crear(Usuario usuario);
    Usuario          actualizar(Usuario usuario);
    void             eliminar(UUID id);
}
