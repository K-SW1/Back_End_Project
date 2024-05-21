package ksw.BackEnd.RecallQuest.member.service;

import ksw.BackEnd.RecallQuest.common.Exception.login.LoginIdAlreadyExistsException;
import ksw.BackEnd.RecallQuest.common.Exception.member.MailAlreadyExistsException;
import ksw.BackEnd.RecallQuest.member.dao.LoginDao;
import ksw.BackEnd.RecallQuest.member.dao.MemberDao;
import ksw.BackEnd.RecallQuest.entity.Login;
import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.member.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberDao memberDao;
    private final LoginDao loginDao;

    @Override
    public Member saveMember(MemberSaveRequestDto memberSaveRequestDto) {

        Member member = Member.builder()
                .name(memberSaveRequestDto.getName())
                .phoneNumber(memberSaveRequestDto.getPhoneNumber())
                .mail(memberSaveRequestDto.getMail())
                .build();

        Login login = Login.builder()
                .userLoginId(memberSaveRequestDto.getUserLoginId())
                .userLoginPassword(bCryptPasswordEncoder.encode(memberSaveRequestDto.getUserLoginPassword()))
                .role("ROLE_USER")
                .member(member)
                .build();

        existsByMail(member.getMail());
        existsByUserLoginId(login.getUserLoginId());

        loginDao.save(login);
        return memberDao.save(member);
    }

    /*
    로그인 아이디 중복 검사
     */
    private void existsByUserLoginId(String loginId) {
        Boolean result = loginDao.existsByUserLoginId(loginId);
        if (result) {
            throw new LoginIdAlreadyExistsException("이미 존재하는 아이디입니다.");
        }
    }

    /*
    회원 이메일 중복 검사
     */
    private void existsByMail(String mail) {
        Boolean result = memberDao.existsByMail(mail);
        if (result) {
            throw new MailAlreadyExistsException("이미 존재하는 메일입니다.");
        }
    }

    @Override
    public Member findMember(String memberName) {
        Member member = memberDao.findByName(memberName);
        return member;
    }

    @Override
    public Member findMember(Long memberSeq) {
        Member member = memberDao.findByMemberSeq(memberSeq);
        return member;
    }

    @Override
    public Member findMemberId(String userLoginId) {
        Login login = loginDao.findByUserLoginId(userLoginId);
        Member member = memberDao.findByMemberSeq(login.getMember().getMemberSeq());
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
