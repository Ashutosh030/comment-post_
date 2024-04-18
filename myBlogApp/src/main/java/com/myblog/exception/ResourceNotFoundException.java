package com.myblog.exception;

public class ResourceNotFoundException extends  RuntimeException{

    //generate constructor
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
