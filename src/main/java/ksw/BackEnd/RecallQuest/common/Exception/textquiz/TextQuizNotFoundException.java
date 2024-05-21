package ksw.BackEnd.RecallQuest.common.Exception.textquiz;

import jakarta.persistence.EntityNotFoundException;

public class TextQuizNotFoundException extends EntityNotFoundException {

    private Integer status;

    public TextQuizNotFoundException() {}

    public TextQuizNotFoundException(String message) {
        super(message);
    }

    public TextQuizNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
