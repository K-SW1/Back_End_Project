package ksw.BackEnd.RecallQuest.common.Exception.distractorImage;

import jakarta.persistence.EntityNotFoundException;

public class DistractorImageNotFoundException extends EntityNotFoundException {

    private Integer status;
    DistractorImageNotFoundException (){}
    public DistractorImageNotFoundException (String message) {
        super(message);
    }
    DistractorImageNotFoundException (String message, Integer status) {
        super(message);
        this.status = status;
    }
}
