package ksw.BackEnd.RecallQuest.common.Exception.Imagequiz;

import jakarta.persistence.EntityNotFoundException;

public class ImageQuizAlreadyExistsException extends EntityNotFoundException {

    private Integer status;

    public ImageQuizAlreadyExistsException() {}

    public ImageQuizAlreadyExistsException(String message) {
        super(message);
    }

    public ImageQuizAlreadyExistsException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
