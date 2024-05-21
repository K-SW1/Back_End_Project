package ksw.BackEnd.RecallQuest.common.Exception.questionimage;

import jakarta.persistence.EntityNotFoundException;

public class QuestionImageNotFoundException extends EntityNotFoundException {

    private Integer status;

    QuestionImageNotFoundException(){}
    public QuestionImageNotFoundException(String message) {
        super(message);
    }
    QuestionImageNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
