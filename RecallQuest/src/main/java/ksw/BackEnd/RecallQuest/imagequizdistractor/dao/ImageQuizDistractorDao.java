package ksw.BackEnd.RecallQuest.imagequizdistractor.dao;

import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;

import java.util.List;

public interface ImageQuizDistractorDao {

    ImageQuizDistractor findByImageQuizDistractor(String imageQuizDistractor);

    List<ImageQuizDistractor> findByImageQuizImageQuizSeq(Long imageQuizId);


}
