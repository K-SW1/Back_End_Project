package ksw.BackEnd.RecallQuest.common.Exception.diary;

import jakarta.persistence.EntityNotFoundException;

public class diaryNotFoundException extends EntityNotFoundException {


    private Integer status;

    public diaryNotFoundException() {}

    public diaryNotFoundException(String message) {
        super(message);
    }

    public diaryNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }


}
