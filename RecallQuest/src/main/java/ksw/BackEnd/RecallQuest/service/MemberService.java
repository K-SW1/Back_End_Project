package ksw.BackEnd.RecallQuest.service;

import ksw.BackEnd.RecallQuest.domain.Member;
import ksw.BackEnd.RecallQuest.dto.MemberSaveRequestDto;

import java.util.List;

public interface MemberService {

    Member saveMember(MemberSaveRequestDto memberSaveRequestDto);

    Member findMember(String name);

    Member findMember(Long memberSeq);

    List<Member> findMembers();

    Member updateMember(MemberSaveRequestDto memberSaveRequestDto);

    Member deleteMember(Long memberSeq);


}
