package ksw.BackEnd.RecallQuest.imagequiz.dao;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.QuestionImage;

public interface QuestionImageDao {
    QuestionImage save(QuestionImage questionImage);

    QuestionImage findByStoreFilename(String storeFilename);

    void deleteByImageQuiz(ImageQuiz imageQuiz);

}
