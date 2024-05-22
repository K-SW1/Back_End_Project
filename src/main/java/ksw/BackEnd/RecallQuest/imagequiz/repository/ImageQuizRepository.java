package ksw.BackEnd.RecallQuest.imagequiz.repository;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {
    
    //문제 내용으로 퀴즈 내용 조회
    Optional<ImageQuiz> findByQuestion(String question);

    List<ImageQuiz> findByMemberMemberSeq(Long memberSeq);

    //회원 시퀀스로 퀴즈 내용 조회
    @Query("select iq from ImageQuiz iq where iq.member.memberSeq = :memberSeq")
    List<ImageQuiz> findByMemberSeq(Long memberSeq);

    //회원 로그인 아이디로 퀴즈 내용 조회
    @Query("SELECT iq FROM ImageQuiz iq JOIN FETCH iq.member m JOIN FETCH m.login l WHERE l.userLoginId = :userLoginId")
    List<ImageQuiz> findByUserLoginId(String userLoginId);

    //퀴즈 시퀀스로 퀴즈, 이미지 함께 조회
    @Query("SELECT iq FROM ImageQuiz iq LEFT JOIN FETCH iq.questionImages WHERE iq.imageQuizSeq = :id")
    Optional<ImageQuiz> findByIdWithImages(@Param("id") Long id);

    Boolean existsByQuestion(String question);

}
