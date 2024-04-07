package ksw.BackEnd.RecallQuest.member.dao;

import ksw.BackEnd.RecallQuest.DataNotFoundException;
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
    private final LoginRepository loginRepository;

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findByName(String name) {
        return memberRepository.findByName(name).orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원 이름 입니다."));
    }

    @Override
    public Member findMemberSeq(Long memberSeq) {
        return memberRepository.findById(memberSeq).orElseThrow(() -> new DataNotFoundException("회원을 찾을 수 없습니다."));
    }


//    public Login findByUserLoginId(String userLoginId) {
//        return loginRepository.findByUserLoginId(userLoginId).orElseThrow(() -> new DataNotFoundException("회원을 찾을 수 없습니다."));
//    }
//
//    @Override
//    public Member findMemberId(String userLoginId) {
//        Login login = findByUserLoginId(userLoginId);
//        Member member = findMemberSeq(login.getMember().getMemberSeq());
//        return member;
//    }

    @Override
    public List<Member> findAll() {
        List<Member> members = memberRepository.findAll();
        return members;
    }

    @Override
    public Member delete(Long memberSeq) { //이것도 회원 없으면 검증해야 되는 거 아닌가,,??
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new DataNotFoundException("회원을 찾을 수 없습니다."));
        memberRepository.deleteById(memberSeq);
        return member;
    }
}
