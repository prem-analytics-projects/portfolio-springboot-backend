package com.prem.myfirstapp.common.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends ApiException {
    public DuplicateEmailException() {
        super(HttpStatus.CONFLICT, "Email already registered");
    }
}
