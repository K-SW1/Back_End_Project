package ksw.BackEnd.RecallQuest.imagequizdistractor.dao;

import ksw.BackEnd.RecallQuest.entity.DistractorImage;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;

public interface DistractorImageDao {

    DistractorImage save(DistractorImage distractorImage);

    void deleteByImageQuizDistractor(ImageQuizDistractor imageQuizDistractor);

    DistractorImage findByStoreFilename(String fileName);


}
