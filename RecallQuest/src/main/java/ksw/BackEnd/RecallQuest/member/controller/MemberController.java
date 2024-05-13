package ksw.BackEnd.RecallQuest.member.controller;

import ksw.BackEnd.RecallQuest.common.KsResponse;
import ksw.BackEnd.RecallQuest.common.code.SuccessCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;
import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.member.dto.MemberResponseDto;
import ksw.BackEnd.RecallQuest.member.dto.MemberSaveRequestDto;
import ksw.BackEnd.RecallQuest.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<ResBodyModel> joinMember(@RequestBody MemberSaveRequestDto memberSaveRequestDto) {

        Member member = memberService.saveMember(memberSaveRequestDto);
        MemberResponseDto memberResponseDto = MemberResponseDto.buildDto(member);
        return KsResponse.toResponse(SuccessCode.SUCCESS, memberResponseDto);
    }

    @GetMapping("/{memberName}")
    public ResponseEntity<ResBodyModel> findByMemberName(@PathVariable String memberName) {

        Member member = memberService.findMember(memberName);
        MemberResponseDto memberResponseDto = MemberResponseDto.buildDto(member);
        return KsResponse.toResponse(SuccessCode.SUCCESS, memberResponseDto);
    }

    @GetMapping("/seq/{memberSeq}")
    public ResponseEntity<ResBodyModel> findByMemberSeq(@PathVariable Long memberSeq) {

        Member member = memberService.findMember(memberSeq);
        MemberResponseDto memberResponseDto = MemberResponseDto.buildDto(member);
        return KsResponse.toResponse(SuccessCode.SUCCESS, memberResponseDto);
    }

    @GetMapping("/loginId/{loginId}")
    public ResponseEntity<ResBodyModel> findByUserLoginId(@PathVariable String loginId) {

        Member member = memberService.findMemberId(loginId);
        MemberResponseDto memberResponseDto = MemberResponseDto.buildDto(member);
        return KsResponse.toResponse(SuccessCode.SUCCESS, memberResponseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> findAllMember(){

        List<Member> members = memberService.findMembers();
        List<MemberResponseDto> memberResponseDtoList = MemberResponseDto.buildMemberDtoList(members);
        return KsResponse.toResponse(SuccessCode.SUCCESS, memberResponseDtoList);
    }

    @PatchMapping("/update")
    public ResponseEntity<ResBodyModel> updateMember(@RequestBody MemberSaveRequestDto memberSaveRequestDto){
        Member member = memberService.updateMember(memberSaveRequestDto);
        MemberResponseDto memberResponseDto = MemberResponseDto.buildDto(member);
        return KsResponse.toResponse(SuccessCode.SUCCESS, memberResponseDto);
    }

    @DeleteMapping("/delete/{userSeq}")
    public ResponseEntity<ResBodyModel> deleteMember(@PathVariable Long userSeq){
        memberService.deleteMember(userSeq);
        return KsResponse.toResponse(SuccessCode.SUCCESS);
    }

}
