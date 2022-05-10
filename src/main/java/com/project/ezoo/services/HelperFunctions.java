package com.project.ezoo.services;

import com.project.ezoo.model.ValidationError;

import java.util.List;

public interface HelperFunctions {
    List<ValidationError> getConstraintViolation(Throwable cause);
    boolean isAuthorizedToMakeChange(String username);
}
