package com.move.task_management_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.move.task_management_api.model.EstadoTarea;

public interface IEstadoTareaRepository extends JpaRepository<EstadoTarea, Integer>{

}
