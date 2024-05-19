package ksw.BackEnd.RecallQuest.imagequizdistractor.repository;

import ksw.BackEnd.RecallQuest.entity.DistractorImage;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistractorImageRepository extends JpaRepository<DistractorImage, Long> {

    void deleteByImageQuizDistractor(ImageQuizDistractor imageQuizDistractor);

//    void deleteByImageQuizDistractorImageQuizDistractorSeq(Long imageQuizDistractorId);
    Optional<DistractorImage> findByStoreFilename(String fileName);
}
