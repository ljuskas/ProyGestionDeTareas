package com.move.task_management_api.util.swagger;

public class ErrorResponseExamples {

    public static final String ERROR_400            = "{\"error\": \"bad_request\",\"estado\": \"Error en datos de entrada\", \"errores\": {\"Mensaje\": \"mensaje de error\"}}";
    public static final String ERROR_400_USER       = "{\"error\": \"validationError\",\"estado\": \"Error de validación\", \"errores\": {\"CLAVE\": \"La clave no puede ser nula\",\"EMAIL\": \"Debe ingresar Email\"}}";
    public static final String ERROR_400_TASK       = "{\"error\": \"validationError\",\"estado\": \"Error de validación\", \"errores\": {\"NOMBRE\": \"Debe ingresar el nombre de la tarea\",\"ESTADO\": \"El estado no debe ser nulo\"}}";
    public static final String ERROR_401            = "{\"error\": \"unauthorized\",\"estado\": \"Error de autenticación: Credenciales incorrectas\", \"errores\": {\"Mensaje\": \"mensaje de error\"}}";
    public static final String ERROR_403            = "{\"error\": \"access_denied\",\"estado\": \"Acceso denegado\", \"errores\": {\"Mensae\": \"mensaje de error\"}}";
    public static final String ERROR_404            = "{\"error\": \"not_found\",\"estado\": \"No se ha encontrado el/los elemento/s solicitado/s\", \"errores\": {\"Mensaje\": \"mensaje de error\"}}";
    public static final String ERROR_409            = "{\"error\": \"user_already_exists\",\"estado\": \"El usuario con email correo_uno@gmail.com ya existe.\", \"errores\": \"null\"}";
    public static final String ERROR_500            = "{\"error\": \"generic_error\",\"estado\": \"Ha ocurrido un error inesperado\", \"errores\": {\"Mensaje\": \"mensaje de error\"}}";

}