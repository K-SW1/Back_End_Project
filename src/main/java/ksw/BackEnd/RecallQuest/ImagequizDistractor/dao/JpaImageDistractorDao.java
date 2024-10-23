package ksw.BackEnd.RecallQuest.ImagequizDistractor.dao;

import ksw.BackEnd.RecallQuest.common.Exception.ImageQuizDistractor.ImageQuizDistractorNotFoundException;
import ksw.BackEnd.RecallQuest.entity.ImagequizDistractor;
import ksw.BackEnd.RecallQuest.ImagequizDistractor.repository.ImageDistractorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaImageDistractorDao {

    private final ImageDistractorRepository imageDistractorRepository;

    // 이미지 선택지 저장
    public ImagequizDistractor save(ImagequizDistractor imageDistractor) {
        return imageDistractorRepository.save(imageDistractor);
    }

    // 여러 개의 이미지 선택지 저장
    public List<ImagequizDistractor> saveAll(List<ImagequizDistractor> imageDistractors) {
        return imageDistractorRepository.saveAll(imageDistractors);
    }

    // 특정 이미지 퀴즈 ID에 해당하는 선택지 목록을 가져오는 메서드
    public List<ImagequizDistractor> findByImageQuiz_ImageQuizId(int imageQuizId) {
        return imageDistractorRepository.findByImageQuiz_ImageQuizId(imageQuizId);
    }

    // 선택지 삭제
    public void deleteAll(List<ImagequizDistractor> imageDistractors) {
        imageDistractorRepository.deleteAll(imageDistractors);
    }

    // 특정 이미지 선택지를 ID로 삭제
    public void deleteByImageDistractorId(ImagequizDistractor imageDistractorId) {
        imageDistractorRepository.deleteByImageDistractorId(imageDistractorId.getImageDistractorId());
    }

    // 특정 ID의 이미지 선택지를 찾는 메서드
    public ImagequizDistractor findById(int imageDistractorId) {
        return imageDistractorRepository.findById(imageDistractorId)
                .orElseThrow(() -> new ImageQuizDistractorNotFoundException("해당 ID의 이미지 선택지를 찾을 수 없습니다."));
    }
}
