package com.prem.myfirstapp.common.exception;

import org.springframework.http.HttpStatus;

// message is a code the React app can switch on:
// EMAIL_NOT_VERIFIED, PENDING_APPROVAL, ACCOUNT_REJECTED
public class AccountNotActiveException extends ApiException {
    public AccountNotActiveException(String code) {
        super(HttpStatus.FORBIDDEN, code);
    }
}
