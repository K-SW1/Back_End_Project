package ksw.BackEnd.RecallQuest.common.Exception.member;

public class MailAlreadyExistsException extends RuntimeException {

    Integer status;

    public MailAlreadyExistsException() {
    }

    public MailAlreadyExistsException(String message) {
        super(message);
    }

    public MailAlreadyExistsException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
