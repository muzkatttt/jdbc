package com.muzkat.jdbc.project.exception;

public class DaoException extends RuntimeException{
    public DaoException(Throwable throwable){
        super(throwable);
    }
}
