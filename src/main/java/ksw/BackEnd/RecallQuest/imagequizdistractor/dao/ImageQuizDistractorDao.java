package ksw.BackEnd.RecallQuest.imagequizdistractor.dao;

import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;

import java.util.List;

public interface ImageQuizDistractorDao {

    ImageQuizDistractor save(ImageQuizDistractor imageQuizDistractor);

    ImageQuizDistractor findByImageQuizDistractor(String imageQuizDistractor);

    List<ImageQuizDistractor> findByImageQuizImageQuizSeq(Long imageQuizId);

    ImageQuizDistractor findById(Long imageQuizDistractorSeq);

    List<ImageQuizDistractor> findByImageQuizSeq(Long imageQuizSeq);

    List<ImageQuizDistractor> findAll();

    void delete(ImageQuizDistractor imageQuizDistractor);
    void deleteById(Long imageQuizDistractorSeq);


}
