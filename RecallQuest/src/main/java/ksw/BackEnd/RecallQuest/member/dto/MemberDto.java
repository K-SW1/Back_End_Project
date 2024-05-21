package ksw.BackEnd.RecallQuest.member.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private long memberId;

    private String name;

    private String phoneNumber;

    private String mail;


}
