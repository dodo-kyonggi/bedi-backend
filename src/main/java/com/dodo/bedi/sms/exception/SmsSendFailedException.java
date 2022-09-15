package com.dodo.bedi.sms.exception;

public class SmsSendFailedException extends Exception{
    public SmsSendFailedException(String msg) {
        super(msg);
    }
}
