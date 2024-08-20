package ksw.BackEnd.RecallQuest.missing.dto;


import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 생성합니다
@AllArgsConstructor(access = AccessLevel.PUBLIC) // 생성자를 공개(public)로 설정합니다
public class MissingResponseDto {

    private String name;

    private String age;

    private String date;

    private String area;

    private String gender;

    private String img;

}
