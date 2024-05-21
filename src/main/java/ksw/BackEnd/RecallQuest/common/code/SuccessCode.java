package ksw.BackEnd.RecallQuest.common.code;

import lombok.Getter;

// 상수 정의함. 그리고 BodyCode 인터페이스 구현.
@Getter
public enum SuccessCode implements BodyCode {

    SUCCESS("PA01", "Done");

    private final String code;
    private final String message;

    SuccessCode(String code, String message){
        this.code = code;
        this.message = message;
    }
}
