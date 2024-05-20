package ksw.BackEnd.RecallQuest.member.dao;

import ksw.BackEnd.RecallQuest.entity.Member;

import java.util.List;

public interface MemberDao {

    Member save(Member member);

    Member findByName(String name);

    Member findByMemberSeq(Long memberSeq);

    Member findByUserLoginId(String userLoginId);

    List<Member> findAll();

    Member delete(Long memberSeq);

    Boolean existsByMail(String mail);


}
