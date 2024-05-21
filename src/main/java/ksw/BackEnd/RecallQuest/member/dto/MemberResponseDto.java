package ksw.BackEnd.RecallQuest.member.dto;

import ksw.BackEnd.RecallQuest.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDto {

    private String name;
    private String phoneNumber;
    private String mail;

    @Builder
    public MemberResponseDto(String name, String phoneNumber, String mail) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    //entity에서 Dto로
    public static MemberResponseDto buildDto (Member member) {
        return MemberResponseDto.builder()
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .mail(member.getMail())
                .build();
    }

    public static List<MemberResponseDto> buildMemberDtoList (List<Member> members) {
        return members.stream().map(member -> {
            return MemberResponseDto.builder()
                    .name(member.getName())
                    .phoneNumber(member.getPhoneNumber())
                    .mail(member.getMail())
                    .build();
        }).collect(Collectors.toList());
    }
}
