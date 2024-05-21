package ksw.BackEnd.RecallQuest.member.dao;

import ksw.BackEnd.RecallQuest.DataNotFoundException;
import ksw.BackEnd.RecallQuest.common.Exception.member.MemberNotFoundException;
import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.member.repository.LoginRepository;
import ksw.BackEnd.RecallQuest.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaMemberDao implements MemberDao{

    private final MemberRepository memberRepository;

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findByName(String name) {
        return memberRepository.findByName(name).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원 이름입니다."));
    }

    @Override
    public Member findByMemberSeq(Long memberSeq) {
        return memberRepository.findById(memberSeq).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원 시퀀스 번호입니다."));
    }

    @Override
    public Member findByUserLoginId(String userLoginId) {
        Member member = findByUserLoginId(userLoginId);
        return member;
    }


    @Override
    public List<Member> findAll() {
        List<Member> members = memberRepository.findAll();
        return members;
    }

    @Override
    public Member delete(Long memberSeq) {
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원 시퀀스 번호입니다."));
        memberRepository.deleteById(memberSeq);
        return member;
    }

    @Override
    public Boolean existsByMail(String mail) {
        return memberRepository.existsByMail(mail);
    }
}
