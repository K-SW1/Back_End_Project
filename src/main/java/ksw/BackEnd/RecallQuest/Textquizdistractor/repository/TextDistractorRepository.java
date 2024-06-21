package ksw.BackEnd.RecallQuest.Textquizdistractor.repository;

import ksw.BackEnd.RecallQuest.entity.TextDistractor;
import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TextDistractorRepository extends JpaRepository<TextDistractor, Integer> {


    Optional<TextDistractor> findById(int textDistractorId);

    List<TextDistractor> findByTextQuiz_TextQuizId(int textQuizId);

    void deleteByTextQuiz(TextQuiz textQuiz); // 트랜잭션으로 묶어서 삭제 하기 위해서 삭제 하고자 할 텍스트퀴즈의 아이디값으로 선택지 먼저 삭제.

    void deleteBytextDistractorId(int textDistractorId); // Delete by TextDistractor ID

}
