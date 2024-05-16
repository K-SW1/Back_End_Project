package csj.BackEnd.RecallQuest.member.service;

import csj.BackEnd.RecallQuest.entity.Member;
import csj.BackEnd.RecallQuest.member.dto.MemberSaveRequestDto;

import java.util.List;

public interface MemberService {

    Member saveMember(MemberSaveRequestDto memberSaveRequestDto);

    Member findMember(String name);

    Member findMember(Long memberSeq);

    Member findMemberId(String userLoginId);

    List<Member> findMembers();

    Member updateMember(MemberSaveRequestDto memberSaveRequestDto);

    Member deleteMember(Long memberSeq);


}
