package com.move.task_management_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomExceptions {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class CustomInternalServerErrorException extends RuntimeException {
        public CustomInternalServerErrorException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class CustomUnauthorizedException extends RuntimeException {
        public CustomUnauthorizedException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class CustomBadRequestException extends RuntimeException {
        public CustomBadRequestException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class CustomNotFoundException extends RuntimeException {
        public CustomNotFoundException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class CustomAccessDeniedException extends RuntimeException {
        public CustomAccessDeniedException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class CustomUserAlreadyExistsException extends RuntimeException {
        public CustomUserAlreadyExistsException(String message) {
            super(message);
        }
    }
}
