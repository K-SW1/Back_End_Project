package csj.BackEnd.RecallQuest.repository;

import csj.BackEnd.RecallQuest.domain.TextChoice;
import csj.BackEnd.RecallQuest.domain.TextQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TextChoiceRepository extends JpaRepository<TextChoice, Integer> {

    List<TextChoice> findByTextQuiz_TextQuizId(int textQuizId);
    void deleteByTextQuiz(TextQuiz textQuiz);
    void deleteByTextChoiceId(Long choiceId);

}
