package ksw.BackEnd.RecallQuest.common.Exception.member;

import jakarta.persistence.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {

    Integer status;

    public MemberNotFoundException(){}

    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
