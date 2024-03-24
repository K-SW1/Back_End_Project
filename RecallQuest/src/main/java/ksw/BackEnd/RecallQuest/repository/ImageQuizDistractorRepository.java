package ksw.BackEnd.RecallQuest.repository;

import ksw.BackEnd.RecallQuest.domain.ImageQuiz;
import ksw.BackEnd.RecallQuest.domain.ImageQuizDistractor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageQuizDistractorRepository extends JpaRepository<ImageQuizDistractor, Long> {
    ImageQuizDistractor findByImageQuizDistractor(String imageQuizDistractor);

}
