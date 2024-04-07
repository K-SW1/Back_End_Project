package ksw.BackEnd.RecallQuest.imagequiz.repository;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {
    ImageQuiz findByQuestion(String question);

    @Query("SELECT iq FROM ImageQuiz iq " +
            "LEFT JOIN FETCH iq.imageQuizDistractors d " +
            "LEFT JOIN FETCH iq.questionImages qi " +
            "WHERE iq.id = :id")
    Optional<ImageQuiz> findByIdWithDistractorsAndImages(@Param("id") Long id);

}
