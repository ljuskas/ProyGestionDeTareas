package com.move.task_management_api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.move.task_management_api.model.Tarea;
import com.move.task_management_api.model.Usuario;


@Repository
public interface ITareaRespository extends JpaRepository<Tarea, UUID>{
    List<Tarea> findAllByUsuario(Usuario usuario);
}
