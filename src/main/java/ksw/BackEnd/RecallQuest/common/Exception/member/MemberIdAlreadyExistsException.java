package ksw.BackEnd.RecallQuest.common.Exception.member;

public class MemberIdAlreadyExistsException extends RuntimeException{

    Integer status;

    public MemberIdAlreadyExistsException() {}

    public MemberIdAlreadyExistsException(String message) {
        super(message);
    }
    public MemberIdAlreadyExistsException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
