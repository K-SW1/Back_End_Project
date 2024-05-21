package ksw.BackEnd.RecallQuest.common.Exception.textquiz;

import jakarta.persistence.EntityNotFoundException;

public class TextQuizAlreadyExistsException extends EntityNotFoundException {

    private Integer status;

    public TextQuizAlreadyExistsException() {}

    public TextQuizAlreadyExistsException(String message) {
        super(message);
    }

    public TextQuizAlreadyExistsException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
