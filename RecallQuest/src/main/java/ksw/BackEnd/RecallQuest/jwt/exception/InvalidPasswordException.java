package ksw.BackEnd.RecallQuest.jwt.exception;

public class InvalidPasswordException extends IllegalArgumentException {

    private Integer status;
    public InvalidPasswordException() {}

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message,Integer status) {
        super(message);
        this.status = status;
    }
}
