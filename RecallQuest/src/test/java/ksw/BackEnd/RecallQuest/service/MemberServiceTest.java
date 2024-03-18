package ksw.BackEnd.RecallQuest.service;

import ksw.BackEnd.RecallQuest.domain.Login;
import ksw.BackEnd.RecallQuest.domain.Member;
import ksw.BackEnd.RecallQuest.dto.LoginDto;
import ksw.BackEnd.RecallQuest.dto.MemberDto;
import ksw.BackEnd.RecallQuest.dto.MemberSaveRequestDto;
import ksw.BackEnd.RecallQuest.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //스프링부트 띄우고 테스트
@Transactional //기본적으로 테스트케이스에 있으면 롤백을 함
class MemberServiceTest {

    @Autowired
    MemberService1 memberService;
    MemberRepository memberRepository;

//    @Test
//    void 회원가입() {
//        // given
//        MemberDto memberDto = createMember1Dto();
//        LoginDto loginDto = createLogin1Dto();
//
//        // when
//        Long savedId = memberService.join(memberDto, loginDto);
//
//        // then
//        MemberDto member = memberService.findMember(savedId);
//        assertEquals(memberDto.getName(), member.getName());
//        assertEquals(memberDto.getPhoneNumber(), member.getPhoneNumber());
//        assertEquals(memberDto.getMail(), member.getMail());
//    }

    @Test //테스트
    void 회원가입() {
        // given
        MemberSaveRequestDto signUpDto = createSignDto();

        // when
        Long savedId = memberService.join(signUpDto); //member테이블 기본키

        // then
        Member member = memberService.findMember(savedId);
        Login login = memberService.findLogin(savedId);

        assertEquals(signUpDto.getName(), member.getName());
        assertEquals(signUpDto.getPhoneNumber(), member.getPhoneNumber());
        assertEquals(signUpDto.getMail(), member.getMail());
        assertEquals(signUpDto.getUserLoginId(), login.getUserLoginId());
        assertEquals(signUpDto.getUserLoginPassword(), login.getUserLoginPassword());
    }

//    @Test
//    public void 회원_단일_조회() {
//        // given
//        MemberDto memberDto = createMember1Dto();
//        LoginDto loginDto = createLogin1Dto();
//        Long savedId = memberService.join(memberDto, loginDto);
//
//        // when
//        MemberDto member = memberService.findMember(savedId);
//
//        // then
//        assertNotNull(member);
//        assertEquals("kim", member.getName());
//        assertEquals("1234", member.getPhoneNumber());
//        assertEquals("kk", member.getMail());
//    }
//
//    @Test
//    public void 회원_전체_조회() {
//        // given
//        MemberDto member1Dto = createMember1Dto(); //회원정보1
//        LoginDto login1Dto = createLogin1Dto(); //로그인정보1
//        Long savedId1 = memberService.join(member1Dto, login1Dto); //로그인 정보1, 회원 테이블의 PK반환
//
//        MemberDto member2Dto = createMember2Dto();//회원정보2
//        LoginDto login2Dto = createLogin2Dto(); //로그인정보2
//        Long savedId2 = memberService.join(member2Dto, login2Dto); //로그인 정보2, 회원 테이블의 PK반환
//
//        // when
//        List<MemberDto> members = memberService.findMembers();
//
//        // then
//        assertEquals(2, members.size());
//
//    }
//
//    @Test
//    void 중복된_로그인_아이디_회원가입_실패() {
//        // given
//        MemberDto member1Dto = createMember1Dto(); //회원정보1
//        LoginDto login1Dto = createLogin1Dto(); //로그인정보1
//        Long savedId1 = memberService.join(member1Dto, login1Dto); //로그인 정보1, 회원 테이블의 PK반환
//
//        MemberDto member2Dto = createMember2Dto();//회원정보2
//
//        // when
//        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
//            memberService.join(member2Dto, createLogin1Dto());
//        });
//
//        //then
//        assertEquals("이미 존재하는 로그인 아이디입니다.", exception.getMessage());
//    }
//
//    @Test
//    public void 회원_정보_수정() {
//        // given
//        MemberDto member1Dto = createMember1Dto(); //회원정보1
//        LoginDto login1Dto = createLogin1Dto(); //로그인정보1
//        Long savedId1 = memberService.join(member1Dto, login1Dto); //로그인 정보1, 회원 테이블의 PK반환
//
//        MemberDto member2Dto = createMember2Dto();//회원정보2
//        LoginDto login2Dto = createLogin2Dto(); //로그인정보2
//        Long savedId2 = memberService.join(member2Dto, login2Dto); //로그인 정보2, 회원 테이블의 PK반환
//
//        // when
//        memberService.updateMember(savedId1, member2Dto);
//        Member savedMember = memberService.findMemberTest(savedId1);
//
//        // then
//        assertEquals(savedMember.getName(), member2Dto.getName());
//        assertEquals(savedMember.getPhoneNumber(), member2Dto.getPhoneNumber());
//        assertEquals(savedMember.getMail(),member2Dto.getMail());
//    }
//
//    @Test
//    public void 회원_정보_삭제() {
//        // given
//        MemberDto member1Dto = createMember1Dto(); //회원정보1
//        LoginDto login1Dto = createLogin1Dto(); //로그인정보1
//        Long savedId1 = memberService.join(member1Dto, login1Dto); //로그인 정보1, 회원 테이블의 PK반환
//
//        // when
//        memberService.deleteMember(savedId1);
//
//        // then
//        assertThrows(DataNotFoundException.class, () -> {
//            memberService.findMember(savedId1);
//        });
//    }


    private static MemberDto createMember1Dto() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("kim");
        memberDto.setPhoneNumber("1234");
        memberDto.setMail("kk");
        return memberDto;
    }

    private static MemberDto createMember2Dto() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("lee");
        memberDto.setPhoneNumber("9999");
        memberDto.setMail("ll");
        return memberDto;
    }

    private static LoginDto createLogin1Dto() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUserLoginId("kkk");
        loginDto.setUserLoginPassword("qwer");
        return loginDto;
    }

    private static LoginDto createLogin2Dto() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUserLoginId("lll");
        loginDto.setUserLoginPassword("qwer");
        return loginDto;
    }

    private static MemberSaveRequestDto createSignDto() {
        MemberSaveRequestDto signUpDto = new MemberSaveRequestDto();
        signUpDto.setUserLoginId("lll");
        signUpDto.setUserLoginPassword("qwer");
        signUpDto.setName("lee");
        signUpDto.setPhoneNumber("9999");
        signUpDto.setMail("ll");
        return signUpDto;
    }

}