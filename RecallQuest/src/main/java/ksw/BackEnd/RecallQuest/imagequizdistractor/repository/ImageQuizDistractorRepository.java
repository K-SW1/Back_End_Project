package ksw.BackEnd.RecallQuest.imagequizdistractor.repository;

import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageQuizDistractorRepository extends JpaRepository<ImageQuizDistractor, Long> {
    ImageQuizDistractor findByImageQuizDistractor(String imageQuizDistractor);

    List<ImageQuizDistractor> findByImageQuizId(Long imageQuizId);

    @Query("SELECT d FROM ImageQuizDistractor d WHERE d.imageQuiz.id = :id")
    List<ImageQuizDistractor> findDistractorsByQuizId(@Param("id") Long id);
}
