package ksw.BackEnd.RecallQuest.dao;

import ksw.BackEnd.RecallQuest.domain.Member;

import java.util.List;

public interface MemberDao {

    Member save(Member member);

    Member findByName(String name);

    Member findMemberSeq(Long memberSeq);

    List<Member> findAll();

    Member delete(Long memberSeq);


}
