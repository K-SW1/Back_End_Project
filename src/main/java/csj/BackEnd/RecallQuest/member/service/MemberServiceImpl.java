package csj.BackEnd.RecallQuest.member.service;

import csj.BackEnd.RecallQuest.member.dao.LoginDao;
import csj.BackEnd.RecallQuest.member.dao.MemberDao;
import csj.BackEnd.RecallQuest.entity.Login;
import csj.BackEnd.RecallQuest.entity.Member;
import csj.BackEnd.RecallQuest.member.dto.MemberSaveRequestDto;
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

//        String loginId = memberSaveRequestDto.getUserLoginId();
//        validateDuplicateLoginId(loginId);

        Member member = Member.builder()
                .name(memberSaveRequestDto.getName())
                .phoneNumber(memberSaveRequestDto.getPhoneNumber())
                .mail(memberSaveRequestDto.getMail())
                .build();

        Login login = Login.builder()
                .userLoginId(memberSaveRequestDto.getUserLoginId())
                .userLoginPassword(bCryptPasswordEncoder.encode(memberSaveRequestDto.getUserLoginPassword()))
                .role("ROLE_USER")
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
    public Member findMemberId(String userLoginId) {
        Login login = loginDao.findByUserLoginId(userLoginId);
        Member member = memberDao.findMemberSeq(login.getMember().getMemberSeq());
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
