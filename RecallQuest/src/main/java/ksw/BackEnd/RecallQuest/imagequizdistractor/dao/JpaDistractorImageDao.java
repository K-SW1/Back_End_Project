package ksw.BackEnd.RecallQuest.imagequizdistractor.dao;

import ksw.BackEnd.RecallQuest.common.Exception.distractorImage.DistractorImageNotFoundException;
import ksw.BackEnd.RecallQuest.entity.DistractorImage;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import ksw.BackEnd.RecallQuest.imagequizdistractor.repository.DistractorImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaDistractorImageDao implements DistractorImageDao {

    private final DistractorImageRepository distractorImageRepository;

    @Override
    public DistractorImage save(DistractorImage distractorImage) {
        return distractorImageRepository.save(distractorImage);
    }

    @Override
    public void deleteByImageQuizDistractor(ImageQuizDistractor imageQuizDistractor) {
        distractorImageRepository.deleteByImageQuizDistractor(imageQuizDistractor);
    }

    @Override
    public DistractorImage findByStoreFilename(String fileName) {
        return distractorImageRepository.findByStoreFilename(fileName).orElseThrow(() -> new DistractorImageNotFoundException("선택지 이미지가 존재하지 않습니다."));
    }
}
