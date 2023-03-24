package com.example.domainmodelopdrachtspringbootles11.exceptions;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(){
        super();
    }

    public RecordNotFoundException(String message){
        super(message);

    }

}