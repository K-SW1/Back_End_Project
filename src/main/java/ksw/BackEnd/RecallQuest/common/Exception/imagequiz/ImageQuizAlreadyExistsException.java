package ksw.BackEnd.RecallQuest.common.Exception.imagequiz;

import jakarta.persistence.EntityNotFoundException;

public class ImageQuizAlreadyExistsException extends EntityNotFoundException {

    private Integer status;

    ImageQuizAlreadyExistsException(){}
    public ImageQuizAlreadyExistsException(String message) {
        super(message);
    }
    ImageQuizAlreadyExistsException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}