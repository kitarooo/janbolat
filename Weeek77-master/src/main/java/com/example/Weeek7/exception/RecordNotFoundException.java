package com.example.Weeek7.exception;

public class RecordNotFoundException extends  RuntimeException{
    public RecordNotFoundException(String message){
        super(message);
    }
}
