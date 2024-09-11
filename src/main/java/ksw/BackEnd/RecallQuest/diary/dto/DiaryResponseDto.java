package ksw.BackEnd.RecallQuest.diary.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 생성합니다
@AllArgsConstructor(access = AccessLevel.PUBLIC) // 생성자를 공개(public)로 설정합니다
public class DiaryResponseDto {

    private int diaryId;

    private Long memberSeq;

    private String name;

    private String time;

    private String memo;

    private String date;  //LocalDate

}
