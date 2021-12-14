package com.informatorio.startups.exception;

public class UserCreationException extends RuntimeException{
    private String message;

    public UserCreationException(String message){
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
