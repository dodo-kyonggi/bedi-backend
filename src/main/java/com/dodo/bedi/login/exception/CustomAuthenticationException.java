package com.dodo.bedi.login.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

    public CustomAuthenticationException(String msg) {
        super(msg);
    }

}