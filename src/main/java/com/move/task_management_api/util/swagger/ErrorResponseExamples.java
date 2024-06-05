package com.move.task_management_api.util.swagger;

public class ErrorResponseExamples {

    public static final String ERROR_400 = "{\"error\": \"Bad Request\",\"estado\": \"400\", \"errores\": {\"campo1\": \"mensaje de error\",\"campo2\": \"mensaje de error\"}}";

    public static final String ERROR_401 = "{\"error\": \"Unauthorized\",\"estado\": \"401\", \"errores\": {\"campo1\": \"mensaje de error\",\"campo2\": \"mensaje de error\"}}";

    public static final String ERROR_404 = "{\"error\": \"Not Found\",\"estado\": \"404\", \"errores\": {\"campo1\": \"mensaje de error\",\"campo2\": \"mensaje de error\"}}";

    public static final String ERROR_500 = "{\"error\": \"Internal Server Error\",\"estado\": \"500\", \"errores\": {\"campo1\": \"mensaje de error\",\"campo2\": \"mensaje de error\"}}";

}