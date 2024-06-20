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
    // TextQuiz 선택지랑 정답 단일 추가
    @PostMapping("/{textQuizId}/distractor/add")
    public ResponseEntity<ResBodyModel> addTextDistractorToQuiz(
            @PathVariable("textQuizId") int textQuizId,
            @RequestBody TextDistractorRequestDto distractorRequestDto
    ) throws IOException {
        TextDistractor savedDistractor = textDistractorService.addTextDistractorToQuiz(textQuizId, distractorRequestDto);
        TextDistractorResponseDto responseDto = quizMapper.toDto(savedDistractor);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }



    // TextQuiz 선택지랑 정답 리스트 형식으로 추가
    @PostMapping("/{textQuizId}/distractors/add")
    public ResponseEntity<ResBodyModel> addTextDistractorsToQuiz(
            @PathVariable("textQuizId") int textQuizId,
            @RequestBody List<TextDistractor> distractors
    ) throws IOException {
        List<TextDistractorRequestDto> requestDtos = quizMapper.toRequestDtoList(distractors);
        List<TextDistractor> savedDistractors = textDistractorService.addTextDistractorsToQuiz(textQuizId, requestDtos);
        List<TextDistractorResponseDto> responseDtos = quizMapper.toDtoList(savedDistractors);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }
    





    /**
     *조회 서비스
     */

    // 단일 TextQuiz 선택지랑 정답 조회
    @GetMapping("/{textQuizId}/distractors")
    public ResponseEntity<ResBodyModel> getTextDistractorsByQuizId(@PathVariable("textQuizId") int textQuizId) {
        List<TextDistractorResponseDto> textDistractors = textDistractorService.getTextDistractorsByQuizId(textQuizId);
        return KsResponse.toResponse(SuccessCode.SUCCESS, textDistractors);
    }


    // TextQuiz 문제랑 힌트 및 선택지랑 정답 특정 조회
    @GetMapping("/{textQuizId}/details")
    public ResponseEntity<ResBodyModel> getTextQuizWithDistractors(@PathVariable int textQuizId) {
        // 텍스트 퀴즈와 해당하는 선택지를 가져오는 서비스 메서드를 호출합니다.
        TextQuizWithDistractorsResponseDto responseDto = textDistractorService.getTextQuizWithDistractors(textQuizId);

        // AetResponse를 사용하여 응답을 반환합니다.
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }


    // TextQuiz 문제랑 힌트 및 선택지랑 정답 전체 조회
    @GetMapping("/every")
    public ResponseEntity<ResBodyModel> getAllTextQuizzesWithDistractors() {
        // 모든 텍스트 퀴즈와 해당하는 선택지를 가져오는 서비스 메서드를 호출합니다.
        List<TextQuizWithDistractorsResponseDto> responseDtos = textDistractorService.getAllTextQuizzesWithDistractors();

        // AetResponse를 사용하여 응답을 반환합니다.
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }





    /**
     *수정 서비스
     */
    // TextQuiz 선택지랑 정답 수정 컨트롤러
    @PutMapping("/{textQuizId}/distractors/update")
    public ResponseEntity<ResBodyModel> updateTextDistractors(@PathVariable int textQuizId,
                                                          @RequestBody List<TextDistractorRequestDto> updatedDistractorsRequestDto) {
        // 서비스 메서드 호출
        List<TextDistractorResponseDto> updatedDistractorsResponseDto = textDistractorService.updateTextDistractors(textQuizId, updatedDistractorsRequestDto);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return KsResponse.toResponse(SuccessCode.SUCCESS, updatedDistractorsResponseDto);
    }


    /**
     *삭제 서비스
     */
    // 텍스트퀴즈선택지 단일 삭제
    @DeleteMapping("/distractor/{textDistractorId}/delete")
    public ResponseEntity<ResBodyModel> deleteTextDistractor(@PathVariable int textDistractorId) {
        textDistractorService.deleteTextDistractor(textDistractorId);
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }
    
    // 텍스트퀴즈선택지 다수 삭제
    @DeleteMapping("/textquiz/{textQuizId}/distractors/delete")
    public ResponseEntity<ResBodyModel> deleteAllDistractorsByTextQuizId(@PathVariable int textQuizId) {
        textDistractorService.deleteAllDistractorsByTextQuizId(textQuizId);
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }


}

