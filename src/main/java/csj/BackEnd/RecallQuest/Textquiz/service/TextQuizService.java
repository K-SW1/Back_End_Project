package csj.BackEnd.RecallQuest.Textquiz.service;

import csj.BackEnd.RecallQuest.Textquiz.dao.JpaTextQuizDao;
import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizRequestDto;
import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizResponseDto;
import csj.BackEnd.RecallQuest.entity.Login;
import csj.BackEnd.RecallQuest.entity.Member;
import csj.BackEnd.RecallQuest.entity.TextQuiz;
import csj.BackEnd.RecallQuest.member.dao.LoginDao;
import csj.BackEnd.RecallQuest.member.dao.MemberDao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextQuizService {

    private final JpaTextQuizDao jpaTextQuizDao;
    private final MemberDao memberDao;
    private final LoginDao loginDao;


    // [TextQuiz] 추가 서비스
    public TextQuizResponseDto addTextQuiz(TextQuizRequestDto requestDto) {

        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userLoginId = authentication.getName(); // 로그인 후 api 요청 시 userLoginId에는 username 값 넣음.


        // 요청 DTO에 사용자의 로그인 ID 설정
        requestDto.setUserLoginId(userLoginId);

        //String userLoginId = requestDto.getUserLoginId();
        Login login = loginDao.findByUserLoginId(requestDto.getUserLoginId());
        //로그인 된 사용자 찾아서 해당 멤버의 시퀀스 넘버를 넣어줌.
        Member member = memberDao.findMemberSeq(login.getMember().getMemberSeq());


        TextQuiz textQuiz = TextQuiz.builder()
                .member(member)
                .question(requestDto.getQuestion())
                .hint(requestDto.getHint())
                .build();

        TextQuiz savedTextQuiz = jpaTextQuizDao.save(textQuiz);

        return TextQuizResponseDto.builder()
                .textQuizId(savedTextQuiz.getTextQuizId())
                .member(member)
                .question(savedTextQuiz.getQuestion())
                .hint(savedTextQuiz.getHint())
                .build();
    }

    // [TextQuiz] 조회 서비스
    public List<TextQuiz> getAllTextQuizzes() {
        return jpaTextQuizDao.findAll();
    }

    // [TextQuiz] 특정 조회 서비스
    public TextQuizResponseDto getTextQuizById(int textQuizId) {
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        return TextQuizResponseDto.builder()
                .textQuizId(textQuiz.getTextQuizId())
                .member(textQuiz.getMember())
                .question(textQuiz.getQuestion())
                .hint(textQuiz.getHint())
                .build();
    }

    // [TextQuiz] 수정 서비스
    public TextQuizResponseDto updateTextQuiz(int textQuizId, TextQuizRequestDto updatedTextQuizRequestDto) {
        TextQuiz existingTextQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        existingTextQuiz.setQuestion(updatedTextQuizRequestDto.getQuestion());
        existingTextQuiz.setHint(updatedTextQuizRequestDto.getHint());

        TextQuiz updatedTextQuiz = jpaTextQuizDao.save(existingTextQuiz);

        return TextQuizResponseDto.builder()
                .textQuizId(updatedTextQuiz.getTextQuizId())
                .member(updatedTextQuiz.getMember())
                .question(updatedTextQuiz.getQuestion())
                .hint(updatedTextQuiz.getHint())
                .build();
    }

    // [TextQuiz][TextChoice] 삭제 서비스 //[TextChoice]먼저 삭제 후 [TextQuiz] 삭제 되는 방식. 외래키 제약 조건 //트래젝션은 두가지 일을 하나로 묶어서 진행하되, 중간에 오류 발생 시 롤백.
    @Transactional
    public void deleteTextQuiz(int textQuizId) {
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        jpaTextQuizDao.delete(textQuiz);
    }

}
