package csj.BackEnd.RecallQuest.member.dao;

import csj.BackEnd.RecallQuest.entity.Member;

import java.util.List;

public interface MemberDao {

    Member save(Member member);

    Member findByName(String name);

    Member findMemberSeq(Long memberSeq);

//    Member findMemberId(String userLoginId);

    List<Member> findAll();

    Member delete(Long memberSeq);


}
