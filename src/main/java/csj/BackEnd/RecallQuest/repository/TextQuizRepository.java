package csj.BackEnd.RecallQuest.repository;

import csj.BackEnd.RecallQuest.domain.TextQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TextQuizRepository extends JpaRepository<TextQuiz, Integer> {

    TextQuiz findTextQuizBymemberInfoId(long id);

    void deleteByTextQuizId(Long textQuizId);


}
