package csj.BackEnd.RecallQuest.repository;

import csj.BackEnd.RecallQuest.domain.TextQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TextQuizRepository extends JpaRepository<TextQuiz, Integer> {

    Optional<TextQuiz> findById(int textQuizId);

    TextQuiz findTextQuizBymemberInfoId(long id); // memberInfoId 값 확인 후 TextQuiz를 조회

}
