package ksw.BackEnd.RecallQuest.member.dto;

import lombok.*;

@Getter
@Setter
public class MemberSaveRequestDto {

    private String userLoginId;
    private String userLoginPassword;
    private String name;
    private String phoneNumber;
    private String mail;
}
