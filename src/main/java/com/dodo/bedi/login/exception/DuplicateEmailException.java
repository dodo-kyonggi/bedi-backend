package com.dodo.bedi.login.exception;

import java.io.IOException;

public class DuplicateEmailException extends IOException {

    public DuplicateEmailException(String msg) {
        super(msg);
    }
}
