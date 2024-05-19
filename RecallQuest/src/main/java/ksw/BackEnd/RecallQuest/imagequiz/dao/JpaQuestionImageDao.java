package ksw.BackEnd.RecallQuest.imagequiz.dao;

import ksw.BackEnd.RecallQuest.common.Exception.questionimage.QuestionImageNotFoundException;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.QuestionImage;
import ksw.BackEnd.RecallQuest.imagequiz.repository.QuestionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaQuestionImageDao implements QuestionImageDao{

    private final QuestionImageRepository questionImageRepository;

    @Override
    public QuestionImage save(QuestionImage questionImage) {
        return questionImageRepository.save(questionImage);
    }

    @Override
    public QuestionImage findByStoreFilename(String storeFilename) {
        return questionImageRepository.findByStoreFilename(storeFilename).orElseThrow(() -> new QuestionImageNotFoundException("존재하지 않는 퀴즈 이미지 입니다."));
    }

    @Override
    public void deleteByImageQuiz(ImageQuiz imageQuiz) {
        questionImageRepository.deleteByImageQuiz(imageQuiz);
    }
}
