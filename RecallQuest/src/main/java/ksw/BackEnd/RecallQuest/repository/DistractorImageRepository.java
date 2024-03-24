package ksw.BackEnd.RecallQuest.repository;

import ksw.BackEnd.RecallQuest.domain.DistractorImage;
import ksw.BackEnd.RecallQuest.domain.ImageQuiz;
import ksw.BackEnd.RecallQuest.domain.ImageQuizDistractor;
import ksw.BackEnd.RecallQuest.domain.QuestionImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistractorImageRepository extends JpaRepository<DistractorImage, Long> {

    Optional<DistractorImage> findByOriginFilename(String fileName);

    void deleteByImageQuizDistractor(ImageQuizDistractor imageQuizDistractor);
}
