package ksw.BackEnd.RecallQuest.jwt.exception;

public class MissingAuthorizationException extends RuntimeException{

    private Integer status;

    public MissingAuthorizationException(){}

    public MissingAuthorizationException(String message) {
        super(message);
    }

    public MissingAuthorizationException(String message, Integer status) {
        super(message);
        this.status = status;
    }

}

