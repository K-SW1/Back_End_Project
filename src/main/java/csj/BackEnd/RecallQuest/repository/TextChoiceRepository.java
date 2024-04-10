package csj.BackEnd.RecallQuest.repository;

import csj.BackEnd.RecallQuest.domain.TextChoice;
import csj.BackEnd.RecallQuest.domain.TextQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TextChoiceRepository extends JpaRepository<TextChoice, Integer> {


    List<TextChoice> findByTextQuiz_TextQuizId(int textQuizId);

    void deleteByTextQuiz(TextQuiz textQuiz); // 트랜잭션으로 묶어서 삭제 하기 위해서 삭제 하고자 할 텍스트퀴즈의 아이디값으로 선택지 먼저 삭제.

}
