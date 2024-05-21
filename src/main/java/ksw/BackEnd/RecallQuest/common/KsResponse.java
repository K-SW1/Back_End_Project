package ksw.BackEnd.RecallQuest.common;

import ksw.BackEnd.RecallQuest.common.code.BodyCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


// ResBodyModel 이미 초기값 후 BodyCode에서 코드와 메시지 GET으로 가져와서 채워주고, 나머지 채워서 객체 생성.

public class KsResponse {

    // BodyCode를 받아와서 ResBodyModel을 생성
    private static ResBodyModel toBody(BodyCode bodyCode) {
        return ResBodyModel.builder()
                .code(bodyCode.getCode())
                .description(bodyCode.getMessage())
                .dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"))) // 현재 시간을 포맷에 맞춰서 날짜/시간 문자열
                .data(null)
                .build();
    }

    // + data
    private static ResBodyModel toBody(BodyCode bodyCode,Object data) {
        return ResBodyModel.builder()
                .code(bodyCode.getCode())
                .description(bodyCode.getMessage())
                .dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")))
                .data(data)
                .build();
    }

    // 위에 두 메서드를 통해 응답 바디 모델이 생성 후 여기서 ResponseEntity로 감싸서 반환시킴.
    //    public static ResponseEntity<ResBodyModel> toResponse(BodyCode bodyCode, Object body) {
    //        return ResponseEntity.ok().body(toBody(bodyCode, body));
    //    }

    public static ResponseEntity<ResBodyModel> toResponse(BodyCode bodyCode) {
        return ResponseEntity.ok().body(toBody(bodyCode));
    }

    public static ResponseEntity<ResBodyModel> toResponse(BodyCode bodyCode, int status) {
        return ResponseEntity.status(status).body(toBody(bodyCode));
    }

    public static ResponseEntity<ResBodyModel> toResponse(BodyCode bodyCode, Object body) {
        return ResponseEntity.ok().body(toBody(bodyCode, body));
    }
    public static ResponseEntity<ResBodyModel> toResponse(BodyCode bodyCode, Object body, int status) {
        return ResponseEntity.status(status).body(toBody(bodyCode, body));
    }

    public static ResponseEntity<ResBodyModel> toResponse(BodyCode bodyCode, Object body, int status, HttpHeaders headers) {
        return ResponseEntity.status(status).headers(headers).body(toBody(bodyCode, body));
    }

}
