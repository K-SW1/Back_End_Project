package ksw.BackEnd.RecallQuest.common.Exception.imagequiz;

import jakarta.persistence.EntityNotFoundException;

public class ImageQuizNotFoundException extends EntityNotFoundException {

    private Integer status;

    ImageQuizNotFoundException(){}
    public ImageQuizNotFoundException(String message) {
        super(message);
    }
    ImageQuizNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
