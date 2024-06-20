package ksw.BackEnd.RecallQuest.Textquiz.controller;


import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import ksw.BackEnd.RecallQuest.Textquiz.service.TextQuizService;
import ksw.BackEnd.RecallQuest.jwt.dto.CustomUserDetails;
import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizRequestDto;
import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizResponseDto;
import ksw.BackEnd.RecallQuest.common.KsResponse;
import ksw.BackEnd.RecallQuest.common.code.SuccessCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;
import ksw.BackEnd.RecallQuest.Textquiz.mapper.TextQuizMapper;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/textquiz")
public class TextQuizController {

    private final TextQuizService textQuizService;
    private final TextQuizMapper quizMapper;

    /**
     *텍스트 퀴즈 저장
     */
    @PostMapping("/add")
    public ResponseEntity<ResBodyModel> addTextQuiz (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody TextQuizRequestDto requestDto
    ) throws IOException {
        requestDto.setUserLoginId(customUserDetails.getUsername());
        TextQuiz savedTextQuiz = textQuizService.addTextQuiz(requestDto);
        TextQuizResponseDto responseDto = quizMapper.toDto(savedTextQuiz);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }


    /**
     *텍스트 퀴즈 수정
     */
    // 텍스트퀴즈/힌트 단일 수정 컨트롤러
    @PutMapping("/{textQuizId}/update")
    public ResponseEntity<ResBodyModel> updateTextQuiz (
            @PathVariable int textQuizId,
            @RequestBody TextQuizRequestDto updatedTextQuizRequestDto
    ) throws IOException {
        TextQuiz updatedTextQuiz = textQuizService.updateTextQuiz(textQuizId, updatedTextQuizRequestDto);
        TextQuizResponseDto responseDto = quizMapper.toDto(updatedTextQuiz);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }


    /**
     *텍스트 퀴즈 조회
     */
    // 텍스트퀴즈/힌트 다수 조회
    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> getAllTextQuizzes() {
        List<TextQuiz> textQuizzes = textQuizService.getAllTextQuizzes();
        List<TextQuizResponseDto> responseDtos = quizMapper.toDtoList(textQuizzes);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }


    // 텍스트퀴즈/힌트 단일 조회
    @GetMapping("/{textQuizId}")
    public ResponseEntity<ResBodyModel> getTextQuizById(@PathVariable int textQuizId) {
        TextQuiz textQuiz = textQuizService.getTextQuizById(textQuizId);
        TextQuizResponseDto responseDto = quizMapper.toDto(textQuiz);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }


    /**
     *텍스트 퀴즈 삭제
     */
    // 텍스트퀴즈 및 텍스트퀴즈선택지 삭제 - 트랜잭션
    @DeleteMapping("/{textQuizId}/delete")
    public ResponseEntity<ResBodyModel> deleteTextQuiz(@PathVariable("textQuizId") int textQuizId) throws IOException {
        textQuizService.deleteTextQuiz(textQuizId);
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }



}
