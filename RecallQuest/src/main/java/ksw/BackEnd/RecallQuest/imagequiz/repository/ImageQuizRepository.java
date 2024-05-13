package ksw.BackEnd.RecallQuest.imagequiz.repository;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {
    Optional<ImageQuiz> findByQuestion(String question);

    List<ImageQuiz> findByMemberMemberSeq(Long memberSeq);

    @Query("select iq from ImageQuiz iq where iq.member.memberSeq = :memberSeq") //여기 질문
    List<ImageQuiz> findByMemberSeq(Long memberSeq);

    // 로그인 ID를 사용하여 이미지 퀴즈 조회하면서 관련 엔티티를 패치 조인으로 불러옴
    @Query("SELECT iq FROM ImageQuiz iq JOIN FETCH iq.member m JOIN FETCH m.login l WHERE l.userLoginId = :userLoginId")
    List<ImageQuiz> findImageQuizzesByUserLoginId(String userLoginId);


    @Query("SELECT iq FROM ImageQuiz iq " +
            "LEFT JOIN FETCH iq.imageQuizDistractors d " +
            "LEFT JOIN FETCH iq.questionImages qi " +
            "WHERE iq.id = :id")
    Optional<ImageQuiz> findByIdWithDistractorsAndImages(@Param("id") Long id);

    // 기본 정보와 이미지 정보만 패치
    @Query("SELECT iq FROM ImageQuiz iq LEFT JOIN FETCH iq.questionImages WHERE iq.id = :id")
    Optional<ImageQuiz> findByIdWithImages(@Param("id") Long id);

    // 디스트랙터 정보는 별도의 메소드에서 처리
    @Query("SELECT d FROM ImageQuizDistractor d WHERE d.imageQuiz.id = :id")
    List<ImageQuizDistractor> findDistractorsByQuizId(@Param("id") Long id);


}
