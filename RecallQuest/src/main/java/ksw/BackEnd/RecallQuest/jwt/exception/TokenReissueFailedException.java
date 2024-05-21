package ksw.BackEnd.RecallQuest.jwt.exception;

public class TokenReissueFailedException extends RuntimeException{

    private Integer status;
    public TokenReissueFailedException(){}
    public TokenReissueFailedException(String  message) {
        super(message);
    }

    public TokenReissueFailedException(String  message,Integer status) {
        super(message);
        this.status = status;
    }
}
