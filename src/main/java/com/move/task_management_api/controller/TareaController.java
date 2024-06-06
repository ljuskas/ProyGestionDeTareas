package com.move.task_management_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.move.task_management_api.dto.TareaDto;
import com.move.task_management_api.dto.request.CreateTareaRequest;
import com.move.task_management_api.dto.request.UpdateTareaRequest;
import com.move.task_management_api.dto.response.ErrorResponse;
import com.move.task_management_api.mapper.TareaMapper;
import com.move.task_management_api.model.Tarea;
import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.service.ITareaService;
import com.move.task_management_api.service.IUsuarioService;
import com.move.task_management_api.service.strategy.ActualizarTareaOperation;
import com.move.task_management_api.service.strategy.CrearTareaOperation;
import com.move.task_management_api.service.strategy.EliminarTareaOperation;
import com.move.task_management_api.util.swagger.ErrorResponseExamples;
import com.move.task_management_api.validation.ValidUUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tareas", description = "Operaciones de gestión de tareas (CRUD)")
@RestController
@RequestMapping("/api/private/tarea")
@SecurityRequirement(name = "bearerAuth")
public class TareaController {

    @Autowired
    private ITareaService tareaService;

    @Autowired
    private CrearTareaOperation crearTareaOperation;

    @Autowired
    private ActualizarTareaOperation actualizarTareaOperation;

    @Autowired
    private EliminarTareaOperation eliminarTareaOperation;

    @Autowired
    private IUsuarioService usuarioService;

    @Operation(summary = "Crear una nueva tarea")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea creada con éxito", content = @Content(schema = @Schema(implementation = TareaDto.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_400_TASK))),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_401))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_500)))
    })
    @PostMapping("/crear")
    public ResponseEntity<TareaDto> crear(
            @Validated @RequestBody 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Detalles de la tarea a crear", required = true, 
                    content = @Content(schema = @Schema(implementation = CreateTareaRequest.class))) 
            CreateTareaRequest tareaRequest, 
            @AuthenticationPrincipal UserDetails userDetail) {
        
                String userEmail = userDetail.getUsername();
                Usuario usuario = usuarioService.obtenerPorEmail(userEmail);
                Tarea tarea = TareaMapper.INSTANCE.toEntity(tareaRequest);
                tarea.setUsuario(usuario);
                
                tareaService.ejecutarOperacion(tarea, crearTareaOperation);
                return ResponseEntity.ok(TareaMapper.INSTANCE.toDto(tarea));
    }

    @Operation(summary = "Obtener una tarea por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea obtenida con éxito", content = @Content(schema = @Schema(implementation = TareaDto.class))),
        @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_403))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_404))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_500)))
    })
    @GetMapping("/{tareaId}")
    public ResponseEntity<TareaDto> obtenerPorId(@ValidUUID @PathVariable String tareaId) {
        UUID uuid = UUID.fromString(tareaId);
        return ResponseEntity.ok(TareaMapper.INSTANCE.toDto(tareaService.obtenerPorId(uuid)));
    }

    @Operation(summary = "Listar todas las tareas del usuario logeado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida con éxito", content = @Content(schema = @Schema(implementation = TareaDto.class, type = "array"))),
        @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_403))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_404))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_500)))
    })
    @GetMapping("/all/byuser")
    public ResponseEntity<List<TareaDto>> listarPorUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Usuario usuario = usuarioService.obtenerPorEmail(userEmail);

        List<Tarea> tareas = tareaService.listarPorUsuario(usuario);
        return ResponseEntity.ok(TareaMapper.INSTANCE.toDtoList(tareas));
    }

    

    @Operation(summary = "Listar tareas por estado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida con éxito", content = @Content(schema = @Schema(implementation = TareaDto.class, type = "array"))),
        @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_403))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_404))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_500)))
    })
    @GetMapping("/estado/{estadoId}")
    public ResponseEntity<List<TareaDto>> listarPorEstado(@PathVariable String estadoId) {
        return ResponseEntity.ok(TareaMapper.INSTANCE.toDtoList(tareaService.listarPorEstado(estadoId)));
    }

    @Operation(summary = "Listar todas las tareas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida con éxito", content = @Content(schema = @Schema(implementation = TareaDto.class, type = "array"))),
        @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_403))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_404))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_500)))
    })
    @GetMapping("/all")
    public ResponseEntity<List<TareaDto>> listar() {
        return ResponseEntity.ok(TareaMapper.INSTANCE.toDtoList(tareaService.listar()));
    }

    @Operation(summary = "Actualizar una tarea")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea actualizada con éxito", content = @Content(schema = @Schema(implementation = TareaDto.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_400_TASK))),
        @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_403))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_404))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_500)))
    })
    @PutMapping("/update")
    public ResponseEntity<TareaDto> actualizar(
            @Validated @RequestBody 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Detalles de la tarea a actualizar", required = true, 
                    content = @Content(schema = @Schema(implementation = UpdateTareaRequest.class))) 
            UpdateTareaRequest tareaRequest, 
            @AuthenticationPrincipal UserDetails userDetail) {
        
        String userEmail = userDetail.getUsername();
        Usuario usuario = usuarioService.obtenerPorEmail(userEmail);
        Tarea tarea = TareaMapper.INSTANCE.toEntity(tareaRequest);
        tarea.setUsuarioModificador(usuario.getId());
        
        Tarea tareaActualizada = actualizarTareaOperation.actualizar(tarea);
        
        return ResponseEntity.ok(TareaMapper.INSTANCE.toDto(tareaActualizada));
    }

    @Operation(summary = "Eliminar una tarea por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea eliminada con éxito", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_403))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_404))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = ErrorResponseExamples.ERROR_500)))
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminar(@ValidUUID @PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        Tarea tarea = tareaService.obtenerPorId(uuid);
        tareaService.ejecutarOperacion(tarea, eliminarTareaOperation);
        return ResponseEntity.ok("Tarea eliminada satisfactoriamente.");
    }
}
