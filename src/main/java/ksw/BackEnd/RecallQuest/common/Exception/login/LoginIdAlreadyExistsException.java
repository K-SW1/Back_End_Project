package ksw.BackEnd.RecallQuest.common.Exception.login;

import jakarta.persistence.EntityNotFoundException;

public class LoginIdAlreadyExistsException extends EntityNotFoundException {

    Integer status;

    public LoginIdAlreadyExistsException(){}

    public LoginIdAlreadyExistsException(String message) {
        super(message);
    }

    public LoginIdAlreadyExistsException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}