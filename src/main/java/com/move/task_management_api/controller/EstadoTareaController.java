package com.move.task_management_api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.move.task_management_api.dto.EstadoTareaDto;
import com.move.task_management_api.dto.response.ErrorResponse;
import com.move.task_management_api.mapper.EstadoTareaMapper;
import com.move.task_management_api.service.IEstadoTareaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Estados", description = "Lista los estados de una tarea")
@RestController
@RequestMapping("/api/public/tarea")
public class EstadoTareaController {

   @Autowired
   public IEstadoTareaService estadoTareaService;

   @Operation(summary = "Lista estados")
   @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retorna con éxito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema= @Schema(implementation = EstadoTareaDto.class, type = "array"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
   })
   @GetMapping("/estado")
   public ResponseEntity<List<EstadoTareaDto>> getEstadosTarea() {
      return ResponseEntity.ok(EstadoTareaMapper.INSTANCE.toListDto(estadoTareaService.listar()));
   }

}