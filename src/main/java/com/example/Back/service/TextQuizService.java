package com.example.Back.service;

import com.example.Back.entity.TextQuiz;
import com.example.Back.entity.TextChoice;
import com.example.Back.repository.TextQuizRepository;
import com.example.Back.repository.TextChoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TextQuizService {

    private final TextQuizRepository textQuizRepository;
    private final TextChoiceRepository textChoiceRepository;

    // 모든 TextQuiz 불러옴.
    public List<TextQuiz> getAllTextQuizzes() {
        return textQuizRepository.findAll();
    }
    // 모든 TextChoice 불러옴.
    public List<TextChoice> getAllTextChoices() {
        return textChoiceRepository.findAll();
    }


//조회
    // Member id 값을 가져와서 사용함.
    public TextQuiz getTextQuizByQuizId(Long textQuizId) {
        return textQuizRepository.findTextQuizByMember_info_id(textQuizId);
    }
    // 선택지) 텍스트퀴즈 id 값을 가져와서 사용함.
    public TextChoice getTextChoiceByChoiceId(Long textChoiceId) {
        return textChoiceRepository.findTextQuizBytext_quiz_id(textChoiceId);
    }
//수정
    // 텍스트 퀴즈를 업데이트합니다.
    public TextQuiz updateTextQuiz(TextQuiz textQuiz) {
        return textQuizRepository.save(textQuiz);
    }
    // 선택지 퀴즈를 업데이트합니다.
    public TextChoice updateTextChoice(TextChoice textChoice) {
        return textChoiceRepository.save(textChoice);
    }
//삭제
    // 텍스트 퀴즈를 삭제 후 목록을 반환합니다.
    public List<TextQuiz> deleteTextQuiz(Long textQuizId) {
        textQuizRepository.deleteById(textQuizId);
        return getAllTextQuizzes();
    }
    // 선택지 퀴즈 삭제 후 목록을 반환합니다.
    public List<TextChoice> deleteTextChoice(Long textChoiceId) {
        textChoiceRepository.deleteById(textChoiceId);
        return getAllTextChoices();
    }

//추가
    // 텍스트퀴즈 추가
    public List<TextQuiz> addTextQuizQuestion(TextQuiz textQuiz) {
        textQuizRepository.save(textQuiz);
        return getAllTextQuizzes();
    }
    // 선택지 추가
    public List<TextChoice> addTextQuizChoice(TextChoice textChoice) {
        textChoiceRepository.save(textChoice);
        return getAllTextChoices();
    }
}
