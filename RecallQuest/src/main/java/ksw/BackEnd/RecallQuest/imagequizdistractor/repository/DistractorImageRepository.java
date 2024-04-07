package ksw.BackEnd.RecallQuest.imagequizdistractor.repository;

import ksw.BackEnd.RecallQuest.entity.DistractorImage;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistractorImageRepository extends JpaRepository<DistractorImage, Long> {

    Optional<DistractorImage> findByOriginFilename(String fileName);

    void deleteByImageQuizDistractor(ImageQuizDistractor imageQuizDistractor);
}
