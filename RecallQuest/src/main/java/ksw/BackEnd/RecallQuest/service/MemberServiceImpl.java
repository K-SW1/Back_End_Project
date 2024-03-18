package ksw.BackEnd.RecallQuest.service;

import ksw.BackEnd.RecallQuest.dao.LoginDao;
import ksw.BackEnd.RecallQuest.dao.MemberDao;
import ksw.BackEnd.RecallQuest.domain.Login;
import ksw.BackEnd.RecallQuest.domain.Member;
import ksw.BackEnd.RecallQuest.dto.MemberSaveRequestDto;
import ksw.BackEnd.RecallQuest.repository.LoginRepository;
import ksw.BackEnd.RecallQuest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberDao memberDao;
    private final LoginDao loginDao;

    @Override
    public Member saveMember(MemberSaveRequestDto memberSaveRequestDto) {

//        String loginId = memberSaveRequestDto.getUserLoginId();
//        validateDuplicateLoginId(loginId);

        Member member = Member.builder()
                .name(memberSaveRequestDto.getName())
                .phoneNumber(memberSaveRequestDto.getPhoneNumber())
                .mail(memberSaveRequestDto.getMail())
                .build();

        Login login = Login.builder()
                .userLoginId(memberSaveRequestDto.getUserLoginId())
                .userLoginPassword(memberSaveRequestDto.getUserLoginPassword())
                .member(member) //이렇게 하는 것이 맞는지 여쭤볼 것!!!, 이걸 안하면 외래키가 널이 돼요 ㅠㅠ
                .build();

        loginDao.save(login); //로그인, 비밀번호 저장
        return memberDao.save(member); //회원 객체 반환
    }

//    private void validateDuplicateLoginId(String loginId) {
//        Login existingLogin = loginDao.findByUserLoginId(loginId);
//        if (existingLogin != null) {
//            throw new IllegalStateException("이미 존재하는 로그인 아이디입니다.");
//        }
//    }

    @Override
    public Member findMember(String memberName) {
        Member member = memberDao.findByName(memberName);
        return member;
    }

    @Override
    public Member findMember(Long memberSeq) {
        Member member = memberDao.findMemberSeq(memberSeq);
        return member;
    }

    @Override
    public List<Member> findMembers() {
        List<Member> members = memberDao.findAll();
        return members;
    }

    @Override
    public Member updateMember(MemberSaveRequestDto memberSaveRequestDto) {
        Member member = memberDao.findByName(memberSaveRequestDto.getName());
        member.changeInfo(memberSaveRequestDto);
        return member;
    }

    @Override
    public Member deleteMember(Long memberSeq) {
        Member member = memberDao.delete(memberSeq);
        return member;
    }
}
