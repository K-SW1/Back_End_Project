package ksw.BackEnd.RecallQuest.common.Exception.missing;

import jakarta.persistence.EntityNotFoundException;

public class MissingNotFoundException extends EntityNotFoundException {

    private Integer status;

    public MissingNotFoundException() {}

    public MissingNotFoundException(String message) {
        super(message);
    }

    public MissingNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
