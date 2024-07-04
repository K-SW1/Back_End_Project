package ksw.BackEnd.RecallQuest.Textquizdistractor.controller;

import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizWithDistractorsResponseDto;
import ksw.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorRequestDto;
import ksw.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorResponseDto;
import ksw.BackEnd.RecallQuest.Textquizdistractor.mapper.TextDistractorMapper;
import ksw.BackEnd.RecallQuest.Textquizdistractor.service.TextDistractorService;
import ksw.BackEnd.RecallQuest.common.KsResponse;
import ksw.BackEnd.RecallQuest.common.code.SuccessCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;
import ksw.BackEnd.RecallQuest.entity.TextDistractor;

import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/textquizDistractor")
public class TextDistractorController {



    private final TextDistractorService textDistractorService;
    private final TextDistractorMapper quizMapper;

    /**
     *추가 서비스
     */
    //보기 단일 추가 - 보기랑 정답 추가
    @PostMapping("/{textQuizId}/distractor/add")
    public ResponseEntity<ResBodyModel> addTextDistractorToQuiz(
            @PathVariable("textQuizId") int textQuizId,
            @RequestBody TextDistractorRequestDto distractorRequestDto
    ) throws IOException {
        TextDistractor savedDistractor = textDistractorService.addTextDistractorToQuiz(textQuizId, distractorRequestDto);
        TextDistractorResponseDto responseDto = quizMapper.toResponse(savedDistractor);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }



    //보기 리스트 추가 - 보기랑 정답 추가
    @PostMapping("/{textQuizId}/distractors/add")
    public ResponseEntity<ResBodyModel> addTextDistractorsToQuiz(
            @PathVariable("textQuizId") int textQuizId,
            @RequestBody List<TextDistractor> distractors
    ) throws IOException {
        List<TextDistractorRequestDto> requestDtos = quizMapper.toRequestDistractorList(distractors);
        List<TextDistractor> savedDistractors = textDistractorService.addTextDistractorsToQuiz(textQuizId, requestDtos);
        List<TextDistractorResponseDto> responseDtos = quizMapper.toResponseDistractorList(savedDistractors);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }
    




    /**
     *조회 서비스
     */
    //보기 단일 조회 - 보기랑 정답 조회
    @GetMapping("/{textQuizId}/distractors")
    public ResponseEntity<ResBodyModel> getTextDistractorsByQuizId(@PathVariable("textQuizId") int textQuizId) {
        List<TextDistractor> textDistractors = textDistractorService.getTextDistractorsByQuizId(textQuizId);
        List<TextDistractorResponseDto> responseDtos = quizMapper.toResponseDistractorList(textDistractors);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }


    //    //보기 단일 조회 - 문제랑 힌트 및 보기 정답 조회 (member null) = 텍스트퀴즈에서 사용되는 서비스
    //    @GetMapping("/{textQuizId}/details")
    //    public ResponseEntity<ResBodyModel> getTextQuizWithDistractors(@PathVariable int textQuizId) {
    //        TextQuiz textQuiz = textDistractorService.getTextQuizWithDistractors(textQuizId);
    //        TextQuizWithDistractorsResponseDto responseDto = quizMapper.toResponseDto(textQuiz);
    //        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    //    }
    //
    //
    //    //보기 전체 조회 - 문제랑 힌트 및 보기 정답 조회 (member null) = 텍스트퀴즈에서 사용되는 서비스
    //    @GetMapping("/every")
    //    public ResponseEntity<ResBodyModel> getAllTextQuizzesWithDistractors() {
    //        List<TextQuiz> allTextQuizzes = textDistractorService.getAllTextQuizzes();
    //        List<TextQuizWithDistractorsResponseDto> responseDtos = quizMapper.toResponseTextQuizList(allTextQuizzes);
    //        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    //    }



    /**
     *수정 서비스
     */
    //보기 단일 수정 - 해당 텍스트퀴즈 보기랑 정답 수정
    @PutMapping("/{textQuizId}/distractors/update")
    public ResponseEntity<ResBodyModel> updateTextDistractors (
             @PathVariable int textQuizId,
             @RequestBody List<TextDistractorRequestDto> updatedDistractorsRequestDto) {
        List<TextDistractor> updatedDistractors = textDistractorService.updateTextDistractors(textQuizId, updatedDistractorsRequestDto);
        List<TextDistractorResponseDto> updatedDistractorsResponseDto = quizMapper.toResponseDistractorList(updatedDistractors);
        return KsResponse.toResponse(SuccessCode.SUCCESS, updatedDistractorsResponseDto);
    }



    /**
     *삭제 서비스
     */
    //보기 단일 삭제 - 해당 텍스트퀴즈 보기 삭제
    @DeleteMapping("/distractor/{textDistractorId}/delete")
    public ResponseEntity<ResBodyModel> deleteTextDistractor(@PathVariable int textDistractorId) {
        textDistractorService.deleteTextDistractor(textDistractorId);
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }
    
    //보기 전체 삭제 - 해당 텍스트퀴즈 보기 삭제
    @DeleteMapping("/textquiz/{textQuizId}/distractors/delete")
    public ResponseEntity<ResBodyModel> deleteAllDistractorsByTextQuizId(@PathVariable int textQuizId) {
        textDistractorService.deleteAllDistractorsByTextQuizId(textQuizId);
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }


}

