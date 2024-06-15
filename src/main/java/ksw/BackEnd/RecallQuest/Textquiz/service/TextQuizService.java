package ksw.BackEnd.RecallQuest.Textquiz.service;

import ksw.BackEnd.RecallQuest.Textquiz.dao.JpaTextQuizDao;
import ksw.BackEnd.RecallQuest.Textquiz.dao.TextQuizDao;
import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizRequestDto;
import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizResponseDto;
import ksw.BackEnd.RecallQuest.entity.Login;
import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import ksw.BackEnd.RecallQuest.member.dao.LoginDao;
import ksw.BackEnd.RecallQuest.member.dao.MemberDao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.io.IOException;





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

    /**
     *추가 서비스
     */
    public TextQuiz addTextQuiz(TextQuizRequestDto requestDto) throws IOException {
        Login login = loginDao.findByUserLoginId(requestDto.getUserLoginId());
        Member member = memberDao.findByMemberSeq(login.getMember().getMemberSeq());

        TextQuiz textQuiz = TextQuiz.builder()
                .member(member)
                .question(requestDto.getQuestion())
                .hint(requestDto.getHint())
                .build();

        existsByQuestion(textQuiz.getQuestion());
        jpaTextQuizDao.save(textQuiz);

        return textQuiz;
    }

    /*
    퀴즈 내용 중복 검사
    */
    private void existsByQuestion(String question) {
        Boolean result = jpaTextQuizDao.existsByQuestion(question);
        if (result) {
            throw new IllegalArgumentException("이미 존재하는 퀴즈내용 입니다.");
        }
    }



    /**
     *수정 서비스
     */
    // 문제와 힌트 수정 서비스
    public TextQuizResponseDto updateTextQuiz(int textQuizId, TextQuizRequestDto updatedTextQuizRequestDto) {
        TextQuiz existingTextQuiz = jpaTextQuizDao.findById(textQuizId);

        existsByQuestion(updatedTextQuizRequestDto.getQuestion());

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



    /**
     *조회 서비스
     */
    // 문제와 힌트 조회 서비스
    public List<TextQuiz> getAllTextQuizzes() {
        return jpaTextQuizDao.findAll();
    }


    // [TextQuiz] 특정 조회 서비스
    public TextQuizResponseDto getTextQuizById(int textQuizId) {
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId);
//                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        return TextQuizResponseDto.builder()
                .textQuizId(textQuiz.getTextQuizId())
                .member(textQuiz.getMember())
                .question(textQuiz.getQuestion())
                .hint(textQuiz.getHint())
                .build();
    }






    /**
     *삭제 서비스
     */
    // [TextQuiz][TextChoice] 삭제 서비스 //[TextChoice]먼저 삭제 후 [TextQuiz] 삭제 되는 방식. 외래키 제약 조건 //트래젝션은 두가지 일을 하나로 묶어서 진행하되, 중간에 오류 발생 시 롤백.
    @Transactional
    public void deleteTextQuiz(int textQuizId) {
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId);
//                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        jpaTextQuizDao.delete(textQuiz);
    }


}
