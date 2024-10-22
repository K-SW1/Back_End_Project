package ksw.BackEnd.RecallQuest.common.Exception.Imagequiz;

import jakarta.persistence.EntityNotFoundException;

public class ImageQuizNotFoundException extends EntityNotFoundException {

    private Integer status;

    public ImageQuizNotFoundException() {}

    public ImageQuizNotFoundException(String message) {
        super(message);
    }

    public ImageQuizNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
