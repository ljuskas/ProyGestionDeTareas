package com.move.task_management_api.dto.response;

import java.util.Map;

public record ErrorResponse(String error, String estado, Map<String, String> errores) {}
