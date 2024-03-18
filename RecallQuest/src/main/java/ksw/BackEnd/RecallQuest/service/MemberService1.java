//package ksw.BackEnd.RecallQuest.service;
//
//import ksw.BackEnd.RecallQuest.DataNotFoundException;
//import ksw.BackEnd.RecallQuest.domain.Login;
//import ksw.BackEnd.RecallQuest.domain.Member;
//import ksw.BackEnd.RecallQuest.dto.MemberDto;
//import ksw.BackEnd.RecallQuest.dto.MemberSaveRequestDto;
//import ksw.BackEnd.RecallQuest.repository.LoginRepository;
//import ksw.BackEnd.RecallQuest.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class MemberService1 {
//
//    private final MemberRepository memberRepository;
//    private final LoginRepository loginRepository;
//    private final ModelMapper modelMapper;
//
////    @Transactional
////    public Long join(MemberDto memberDto, LoginDto loginDto) {
////        String loginId = loginDto.getUserLoginId();
////        String loginPassword = loginDto.getUserLoginPassword();
////
////        validateDuplicateLoginId(loginId);
////
////        Member member = new Member();
////        member.setName(memberDto.getName());
////        member.setPhoneNumber(memberDto.getPhoneNumber());
////        member.setMail(memberDto.getMail());
////
////        Login login = new Login();
////        login.setUserLoginId(loginId);
////        login.setUserLoginPassword(loginPassword);
////        login.setMember(member);
////
////        loginRepository.save(login);
////
////        return memberRepository.save(member).getId();
////    }
//
//    //테스트
//    @Transactional
//    public Long join(MemberSaveRequestDto signUpDto) {
//        String loginId = signUpDto.getUserLoginId();
//        String loginPassword = signUpDto.getUserLoginPassword();
//
//        validateDuplicateLoginId(loginId);
//
//        Member member = new Member();
//        member.setName(signUpDto.getName());
//        member.setPhoneNumber(signUpDto.getPhoneNumber());
//        member.setMail(signUpDto.getMail());
//
//        Login login = new Login();
//        login.setUserLoginId(loginId);
//        login.setUserLoginPassword(loginPassword);
//        login.setMember(member);
//
//        loginRepository.save(login);
//
//        return memberRepository.save(member).getId();
//    }
//
//    private void validateDuplicateLoginId(String loginId) {
//        Login existingLogin = loginRepository.findByUserLoginId(loginId);
//        if (existingLogin != null) {
//            throw new IllegalStateException("이미 존재하는 로그인 아이디입니다.");
//        }
//    }
//
//    // 회원 전체 조회
//    public List<MemberDto> findMembers() {
//        List<Member> members = memberRepository.findAll();
//        return members.stream()
//                .map(this::convertToDto) //엔티티를 DTO로 변환
//                .collect(Collectors.toList()); //스트림의 결과를 리스트로 변환하여 반환
//    }
//
////    // 회원 조회
////    public MemberDto findMember(Long memberId) {
////        Member member = memberRepository.findById(memberId)
////                .orElseThrow(() -> new DataNotFoundException("해당 회원이 존재하지 않습니다."));
////        return convertToDto(member);
////    }
//
//    // DTO테스트용 조회 메서드 Member
//    public Member findMember(Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new DataNotFoundException("해당 회원이 존재하지 않습니다."));
//        return member;
//    }
//
//    // DTO테스트용 조회 메서드 Login
//    public Login findLogin(long loginId) {
//        return loginRepository.findById(loginId);
//    }
//
//    //회원_정보_수정_테스트용 조회 메서드
//    public Member findMemberTest(Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new DataNotFoundException("해당 회원이 존재하지 않습니다."));
//        return member;
//    }
//
//    //회원 수정
//    @Transactional
//    public void updateMember(Long memberId, MemberDto updateMemberDto) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new DataNotFoundException("해당 회원이 존재하지 않습니다."));
//        // 엔티티를 DTO에서 받아온 정보로 업데이트
//        member.setName(updateMemberDto.getName());
//        member.setPhoneNumber(updateMemberDto.getPhoneNumber());
//        member.setMail(updateMemberDto.getMail());
//    }
//
//    //회원 삭제
//    @Transactional
//    public void deleteMember(Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new DataNotFoundException("해당 회원이 존재하지 않습니다."));
//        memberRepository.delete(member);
//    }
//
//    // 엔티티를 DTO로 변환하는 메서드
//    private MemberDto convertToDto(Member member) {
//        MemberDto memberDto = new MemberDto();
//        memberDto.setMemberId(member.getId());
//        memberDto.setName(member.getName());
//        memberDto.setPhoneNumber(member.getPhoneNumber());
//        memberDto.setMail(member.getMail());
//
//        return memberDto;
//    }
//
//    // 테스트
//    private MemberSaveRequestDto convertToSignUpDto(Member member) {
//        MemberSaveRequestDto signUpDto = new MemberSaveRequestDto();
//        signUpDto.setName(member.getName());
//        signUpDto.setPhoneNumber(member.getPhoneNumber());
//        signUpDto.setMail(member.getMail());
//
//        return signUpDto;
//    }
//}
