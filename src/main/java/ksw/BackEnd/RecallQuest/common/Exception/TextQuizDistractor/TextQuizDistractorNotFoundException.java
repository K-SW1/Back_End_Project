package ksw.BackEnd.RecallQuest.common.Exception.TextQuizDistractor;

import jakarta.persistence.EntityNotFoundException;

public class TextQuizDistractorNotFoundException extends EntityNotFoundException {

    private Integer status;

    public TextQuizDistractorNotFoundException() {}

    public TextQuizDistractorNotFoundException(String message) {
        super(message);
    }

    public TextQuizDistractorNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
