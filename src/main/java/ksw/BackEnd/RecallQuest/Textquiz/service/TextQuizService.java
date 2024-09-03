package ksw.BackEnd.RecallQuest.Textquiz.service;

import ksw.BackEnd.RecallQuest.Textquiz.dao.JpaTextQuizDao;
import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizRequestDto;
import ksw.BackEnd.RecallQuest.entity.Login;
import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import ksw.BackEnd.RecallQuest.member.dao.LoginDao;
import ksw.BackEnd.RecallQuest.member.dao.MemberDao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;



import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextQuizService {

    private final JpaTextQuizDao jpaTextQuizDao;
    private final MemberDao memberDao;
    private final LoginDao loginDao;

    /**
     * 텍스트 퀴즈 저장
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

        try {
            jpaTextQuizDao.save(textQuiz);
        } catch (DataAccessException e) {
            throw new IOException("텍스트퀴즈 저장 실패", e);
        }

        return textQuiz;
    }


    /**
     * 퀴즈 내용 중복 검사
     */
    private void existsByQuestion(String question) {
        Boolean result = jpaTextQuizDao.existsByQuestion(question);
        if (result) {
            throw new IllegalArgumentException("이미 존재하는 퀴즈내용 입니다.");
        }
    }


    /**
     * 텍스트 퀴즈 수정
     */
    // 텍스트퀴즈/힌트 수정 서비스
    public TextQuiz updateTextQuiz(int textQuizId, TextQuizRequestDto updatedTextQuizRequestDto) throws IOException, IllegalAccessException {
        TextQuiz existingTextQuiz = jpaTextQuizDao.findById(textQuizId);

        String username = getUsernameFromSecurityContext();

        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();

        if (!existingTextQuiz.getMember().getMemberSeq().equals(login.getMember().getMemberSeq())) {
            throw new IllegalAccessException("이 퀴즈에 대한 수정 권한이 없습니다.");
        }

        TextQuiz textQuiz = TextQuiz.builder()
                .textQuizId(existingTextQuiz.getTextQuizId())
                .member(existingTextQuiz.getMember())
                .question(updatedTextQuizRequestDto.getQuestion())
                .hint(updatedTextQuizRequestDto.getHint())
                .build();

        existsByQuestion(updatedTextQuizRequestDto.getQuestion());

        try {
            jpaTextQuizDao.save(textQuiz);
        } catch (DataAccessException e) {
            throw new IOException("텍스트퀴즈 업데이트 실패", e);
        }

        return textQuiz;

    }
    private String getUsernameFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }






    /**
     *텍스트 퀴즈 조회
     */
    //문제보기 전체 조회 - 문제랑 힌트 조회 서비스
    public List<TextQuiz> getAllTextQuizzes() {
        String username = getUsernameFromSecurityContext();
        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();

//      List<TextQuiz> textQuizzes = jpaTextQuizDao.findAll();

        return jpaTextQuizDao.findAllByMember(loggedInMember);

//      return textQuizzes;
    }

    //문제보기 단일 조회 - 문제랑 힌트 조회 서비스
    public TextQuiz getTextQuizById(int textQuizId) throws IllegalAccessException {
        String username = getUsernameFromSecurityContext();
        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();

        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId);

        // 퀴즈 접근 권한 확인
        if (!textQuiz.getMember().getMemberSeq().equals(loggedInMember.getMemberSeq())) {
            throw new IllegalAccessException("이 퀴즈에 대한 조회 권한이 없습니다.");
        }

        return textQuiz;
    }

    //문제보기 단일 조회 - 문제랑 힌트 질문 내용으로 검색 서비스
    public TextQuiz getTextQuizByQuestion(String question) throws IllegalAccessException {
        String username = getUsernameFromSecurityContext();
        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();

        TextQuiz textQuiz = jpaTextQuizDao.findByQuestion(question);

        // 퀴즈 접근 권한 확인
        if (!textQuiz.getMember().getMemberSeq().equals(loggedInMember.getMemberSeq())) {
            throw new IllegalAccessException("이 퀴즈에 대한 조회 권한이 없습니다.");
        }
        return textQuiz;
    }








    /**
     *텍스트 퀴즈 삭제
     */
    // [TextQuiz][TextChoice] 삭제 서비스 //[TextChoice]먼저 삭제 후 [TextQuiz] 삭제 되는 방식. 외래키 제약 조건
    @Transactional
    public void deleteTextQuiz(int textQuizId) throws IOException, IllegalAccessException {

        String username = getUsernameFromSecurityContext();
        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();

        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId);

        if (!textQuiz.getMember().getMemberSeq().equals(loggedInMember.getMemberSeq())) {
            throw new IllegalAccessException("이 퀴즈에 대한 삭제 권한이 없습니다.");
        }

        try {
            jpaTextQuizDao.delete(textQuiz);
        } catch (DataAccessException e) {
            throw new IOException("텍스트퀴즈 삭제를 실패하였습니다.", e);
        }
    }



}
