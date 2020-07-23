package com.brian.springstudy.exception;

public class UnknownEnumCodeException extends RuntimeException {
    public UnknownEnumCodeException(Class cls, String code) {
        super("UnknownEnumCodeException : Enum Code ["+code+"] not in "+cls.getName());
    }
}
