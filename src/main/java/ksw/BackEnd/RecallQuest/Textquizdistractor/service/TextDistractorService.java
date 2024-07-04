package ksw.BackEnd.RecallQuest.Textquizdistractor.service;

import ksw.BackEnd.RecallQuest.Textquiz.dao.JpaTextQuizDao;
import ksw.BackEnd.RecallQuest.Textquizdistractor.dao.JpaTextDistractorDao;
import ksw.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorRequestDto;

import ksw.BackEnd.RecallQuest.entity.TextDistractor;
import ksw.BackEnd.RecallQuest.entity.TextQuiz;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;





@Service
@RequiredArgsConstructor
public class TextDistractorService {


    private final JpaTextQuizDao jpaTextQuizDao;
    private final JpaTextDistractorDao jpaTextDistractorDao;


    /**
     *추가 서비스
     */
    //보기 단일 추가 - 보기랑 정답 추가 서비스
    public TextDistractor addTextDistractorToQuiz(int textQuizId, TextDistractorRequestDto distractorRequestDto) throws IOException {
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId);

        TextDistractor distractor = TextDistractor.builder()
                .textzQuizDistractor(distractorRequestDto.getTextzQuizDistractor())
                .validation(distractorRequestDto.isValidation())
                .textQuiz(textQuiz)
                .build();
        try {
            jpaTextDistractorDao.save(distractor);
        } catch (DataAccessException e) {
            throw new IOException("텍스트퀴즈선택지 조회를 실패하였습니다.", e);
        }

        return distractor;
    }


    //보기 리스트 추가 - 보기랑 정답 추가 서비스
    public List<TextDistractor> addTextDistractorsToQuiz(int textQuizId, List<TextDistractorRequestDto> requestDtos) throws IOException {
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId);

        List<TextDistractor> distractors = requestDtos.stream()
                .map(requestDto -> TextDistractor.builder()
                .textzQuizDistractor(requestDto.getTextzQuizDistractor())
                .validation(requestDto.isValidation())
                .textQuiz(textQuiz)
                .build())
                .collect(Collectors.toList());
        try {
            jpaTextDistractorDao.saveAll(distractors);
        } catch (DataAccessException e) {
            throw new IOException("텍스트퀴즈선택지 조회를 실패하였습니다.", e);
        }

        return distractors;
    }


    
    /**
     *조회 서비스
     */
    //보기 단일 조회 - 보기랑 정답 조회 서비스
    public List<TextDistractor> getTextDistractorsByQuizId(int textQuizId) {
        return jpaTextDistractorDao.findByTextQuiz_TextQuizId(textQuizId);
   }



   //문제보기 단일 조회 - 문제랑 힌트 및 보기 정답 조회 (member null) = 텍스트퀴즈에서 사용되는 서비스
   public TextQuiz getTextQuizWithDistractors(int textQuizId) {
        return jpaTextQuizDao.findById(textQuizId);
  }

   //문제보기 전체 조회 - 문제랑 힌트 및 보기 정답 조회 (member null) = 텍스트퀴즈에서 사용되는 서비스
   public List<TextQuiz> getAllTextQuizzes() {
        return jpaTextQuizDao.findAll();
  }




    /**
     * 수정 서비스
     */
    //보기 단일 수정 - 해당 텍스트퀴즈 보기랑 정답 수정 서비스
    @Transactional
    public List<TextDistractor> updateTextDistractors(int textQuizId, List<TextDistractorRequestDto> updatedDistractorsRequestDto) {
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId);

        List<TextDistractor> existingDistractors = jpaTextDistractorDao.findByTextQuiz_TextQuizId(textQuizId);
        jpaTextDistractorDao.deleteAll(existingDistractors);

        List<TextDistractor> updatedDistractors = updatedDistractorsRequestDto.stream()
                .map(dto -> TextDistractor.builder()
                        .textzQuizDistractor(dto.getTextzQuizDistractor())
                        .validation(dto.isValidation())
                        .textQuiz(textQuiz)
                        .build())
                .collect(Collectors.toList());

        return jpaTextDistractorDao.saveAll(updatedDistractors);
    }




    /**
     *삭제 서비스
     */
    //보기 단일 삭제 - 해당 텍스트퀴즈 보기 삭제 서비스
    @Transactional
    public void deleteTextDistractor(int textDistractorId) {
        TextDistractor textDistractor = jpaTextDistractorDao.findById(textDistractorId);
        jpaTextDistractorDao.deleteByTextDistractorId(textDistractor);
        
    }

    //보기 전체 삭제 - 해당 텍스트퀴즈 보기 삭제 서비스
    @Transactional
    public void deleteAllDistractorsByTextQuizId(int textQuizId) {
        List<TextDistractor> distractors = jpaTextDistractorDao.findByTextQuiz_TextQuizId(textQuizId);
        jpaTextDistractorDao.deleteAll(distractors);
    }
}

