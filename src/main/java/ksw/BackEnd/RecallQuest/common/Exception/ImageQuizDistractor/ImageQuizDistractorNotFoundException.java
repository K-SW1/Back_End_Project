package ksw.BackEnd.RecallQuest.common.Exception.ImageQuizDistractor;

import jakarta.persistence.EntityNotFoundException;

public class ImageQuizDistractorNotFoundException extends EntityNotFoundException {

    private Integer status;

    ImageQuizDistractorNotFoundException(){}
    public ImageQuizDistractorNotFoundException(String message) {
        super(message);
    }
    ImageQuizDistractorNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }

}
