package ksw.BackEnd.RecallQuest.Textquiz.repository;

import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TextQuizRepository extends JpaRepository<TextQuiz, Integer> {

    Optional<TextQuiz> findById(int textQuizId);

    TextQuiz findTextQuizBymember(Member member); // memberInfoId 값 확인 후 TextQuiz를 조회

    boolean existsByQuestion(String question);

    Optional<TextQuiz> findByQuestion(String question);

}
