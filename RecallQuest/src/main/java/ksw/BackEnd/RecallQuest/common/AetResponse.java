package ksw.BackEnd.RecallQuest.common;

import ksw.BackEnd.RecallQuest.common.code.BodyCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AetResponse {

    private static ResBodyModel toBody(BodyCode bodyCode) {
        return ResBodyModel.builder()
                .code(bodyCode.getCode())
                .description(bodyCode.getMessage())
                .dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")))
                .data(null)
                .build();
    }

    private static ResBodyModel toBody(BodyCode bodyCode,Object data) {
        return ResBodyModel.builder()
                .code(bodyCode.getCode())
                .description(bodyCode.getMessage())
                .dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")))
                .data(data)
                .build();
    }

    public static ResponseEntity<ResBodyModel> toResponse(BodyCode bodyCode, Object body) {
        return ResponseEntity.ok().body(toBody(bodyCode, body));
    }


}
