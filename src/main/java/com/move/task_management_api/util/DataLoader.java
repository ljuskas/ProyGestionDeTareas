package com.move.task_management_api.util;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.move.task_management_api.model.EstadoTarea;
import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.repository.IEstadoTareaRepository;
import com.move.task_management_api.service.IUsuarioService;

@Configuration
public class DataLoader {

    @Bean
    @Transactional
    CommandLineRunner loadData(IEstadoTareaRepository estadoRepository, IUsuarioService usuarioService) {
        return args -> {
            estadoRepository.save(new EstadoTarea(1, "Pendiente", "Tarea pendiente de realizar"));
            estadoRepository.save(new EstadoTarea(2, "En progreso", "Tarea en progreso"));
            estadoRepository.save(new EstadoTarea(3, "Completada", "Tarea completada"));
            usuarioService.crear(new Usuario(null, "Sebastian Concha", "correo_uno@gmail.com", null, "12345678", null));
            usuarioService.crear(new Usuario(null, "Raul Sanhueza", "correo_dos@gmail.com", null, "87654321", null));
            usuarioService.crear(new Usuario(null, "Manuel Poblete", "correo_tres@gmail.com", null, "abc12345", null));
        };
    }

}