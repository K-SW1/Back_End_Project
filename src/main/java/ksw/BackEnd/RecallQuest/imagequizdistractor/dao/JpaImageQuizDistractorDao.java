package ksw.BackEnd.RecallQuest.imagequizdistractor.dao;

import ksw.BackEnd.RecallQuest.common.Exception.ImageQuizDistractor.ImageQuizDistractorNotFoundException;
import ksw.BackEnd.RecallQuest.common.Exception.imagequiz.ImageQuizNotFoundException;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import ksw.BackEnd.RecallQuest.imagequiz.repository.ImageQuizRepository;
import ksw.BackEnd.RecallQuest.imagequizdistractor.repository.ImageQuizDistractorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaImageQuizDistractorDao implements ImageQuizDistractorDao{

    private final ImageQuizDistractorRepository imageQuizDistractorRepository;

    @Override
    public ImageQuizDistractor save(ImageQuizDistractor imageQuizDistractor) {
        return imageQuizDistractorRepository.save(imageQuizDistractor);
    }

    @Override
    public ImageQuizDistractor findByImageQuizDistractor(String imageQuizDistractor) {
        return imageQuizDistractorRepository.findByImageQuizDistractor(imageQuizDistractor).orElseThrow(()-> new ImageQuizDistractorNotFoundException("존재하지 않는 이미지 퀴즈 선택지입니다."));
    }

    @Override
    public List<ImageQuizDistractor> findByImageQuizImageQuizSeq(Long imageQuizId) {
        List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorRepository.findByImageQuizImageQuizSeq(imageQuizId);
        return imageQuizDistractors;
    }

    @Override
    public ImageQuizDistractor findById(Long imageQuizDistractorSeq) {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorRepository.findById(imageQuizDistractorSeq).orElseThrow(()-> new ImageQuizDistractorNotFoundException("존재하지 않는 이미지 퀴즈 선택지입니다."));
        return imageQuizDistractor;
    }

    @Override
    public List<ImageQuizDistractor> findByImageQuizSeq(Long imageQuizSeq) {
        List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorRepository.findByImageQuizSeq(imageQuizSeq);
        return imageQuizDistractors;
    }

    @Override
    public List<ImageQuizDistractor> findAll() {
        List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorRepository.findAll();
        return imageQuizDistractors;
    }

    @Override
    public void delete(ImageQuizDistractor imageQuizDistractor) {
        imageQuizDistractorRepository.delete(imageQuizDistractor);
    }

    @Override
    public void deleteById(Long imageQuizDistractorSeq) {
        ImageQuizDistractor imageQuizDistractor = findById(imageQuizDistractorSeq);
        imageQuizDistractorRepository.delete(imageQuizDistractor);
    }
}
