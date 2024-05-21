package ksw.BackEnd.RecallQuest.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResBodyModel {

    private final String code;
    private final String description;
    private final String dateTime;
    private final Object data;

    //초기값 잡아줌.
    @Builder
    public ResBodyModel(String code, String description, String dateTime, Object data) {
        this.code = code;
        this.description = description;
        this.dateTime = dateTime;
        this.data = data;
    }
}
