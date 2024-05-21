package ksw.BackEnd.RecallQuest.jwt.exception;

import lombok.Getter;

@Getter
public class IllegalAccessTokenException extends IllegalArgumentException {

    private Integer status;

    public IllegalAccessTokenException() {
    }

    public IllegalAccessTokenException(String message) {
        super(message);
    }

    public IllegalAccessTokenException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public IllegalAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAccessTokenException(Throwable cause) {
        super(cause);
    }
}
