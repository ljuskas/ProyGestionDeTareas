package com.move.task_management_api.service.strategy;

public interface IPasswordEncoderOperation {
    String  encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
