package com.move.task_management_api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.move.task_management_api.dto.EstadoTareaDto;
import com.move.task_management_api.mapper.EstadoTareaMapper;
import com.move.task_management_api.service.IEstadoTareaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Estados", description = "Lista los estados de una tarea")
@RestController
@RequestMapping("/api/public/tarea")
public class EstadoTareaController {

   @Autowired
   public IEstadoTareaService estadoTareaService;

   @Operation(summary = "Lista estados")
   @GetMapping("/estado")
   public ResponseEntity<List<EstadoTareaDto>> getEstadosTarea() {
      return ResponseEntity.ok(EstadoTareaMapper.INSTANCE.toListDto(estadoTareaService.listar()));
   }

}