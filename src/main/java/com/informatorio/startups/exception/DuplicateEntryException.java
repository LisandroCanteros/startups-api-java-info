package com.informatorio.startups.exception;

public class DuplicateEntryException extends RuntimeException{
    private String message;

    public DuplicateEntryException(String message){
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
