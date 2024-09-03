package ksw.BackEnd.RecallQuest.Textquiz.controller;


import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizWithDistractorsResponseDto;
import ksw.BackEnd.RecallQuest.Textquizdistractor.mapper.TextDistractorMapper;
import ksw.BackEnd.RecallQuest.Textquizdistractor.service.TextDistractorService;
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
    private final TextQuizMapper TextquizMapper;
    private final TextDistractorMapper TextDistractorMapper;
    private final TextDistractorService textDistractorService;

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
        TextQuizResponseDto responseDto = TextquizMapper.toDto(savedTextQuiz);
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
    ) throws IOException, IllegalAccessException {
        TextQuiz updatedTextQuiz = textQuizService.updateTextQuiz(textQuizId, updatedTextQuizRequestDto);
        TextQuizResponseDto responseDto = TextquizMapper.toDto(updatedTextQuiz);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }


    /**
     *텍스트 퀴즈 조회
     */
    //문제보기 전체 조회 - 문제랑 힌트 조회
    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> getAllTextQuizzes() {
        List<TextQuiz> textQuizzes = textQuizService.getAllTextQuizzes();
        List<TextQuizResponseDto> responseDtos = TextquizMapper.toDtoList(textQuizzes);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }


    //문제보기 단일 조회 - 문제랑 힌트 조회
    @GetMapping("/{textQuizId}")
    public ResponseEntity<ResBodyModel> getTextQuizById(@PathVariable int textQuizId) throws IllegalAccessException {
        TextQuiz textQuiz = textQuizService.getTextQuizById(textQuizId);
        TextQuizResponseDto responseDto = TextquizMapper.toDto(textQuiz);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }


    /**
     * 조회 새 기능들 추가
     */
    //문제보기 단일 조회 - 문제랑 힌트 질문 내용으로 검색
    @GetMapping("/question")
    public ResponseEntity<ResBodyModel> getTextQuizByQuestion(@RequestBody TextQuizRequestDto requestDto) throws IllegalAccessException {
        TextQuiz textQuiz = textQuizService.getTextQuizByQuestion(requestDto.getQuestion());
        TextQuizResponseDto responseDto = TextquizMapper.toDto(textQuiz);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }



    //문제보기 단일 조회 - 문제랑 힌트 및 보기 정답 조회  = TextDistractorService
    @GetMapping("/{textQuizId}/details")
    public ResponseEntity<ResBodyModel> getTextQuizWithDistractors(@PathVariable int textQuizId) throws IllegalAccessException {
        TextQuiz textQuiz = textDistractorService.getTextQuizWithDistractors(textQuizId);
        TextQuizWithDistractorsResponseDto responseDto = TextDistractorMapper.toResponseDto(textQuiz);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }


    //문제보기 전체 조회 - 문제랑 힌트 및 보기 정답 조회 = TextDistractorService
    @GetMapping("/every")
    public ResponseEntity<ResBodyModel> getAllTextQuizzesWithDistractors() {
        List<TextQuiz> allTextQuizzes = textDistractorService.getAllTextQuizzes();
        List<TextQuizWithDistractorsResponseDto> responseDtos = TextDistractorMapper.toResponseTextQuizList(allTextQuizzes);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }



    
    /**
     *텍스트 퀴즈 삭제
     */
    // 텍스트퀴즈 및 텍스트퀴즈선택지 삭제 - 트랜잭션
    @DeleteMapping("/{textQuizId}/delete")
    public ResponseEntity<ResBodyModel> deleteTextQuiz(@PathVariable("textQuizId") int textQuizId) throws IOException, IllegalAccessException {
        textQuizService.deleteTextQuiz(textQuizId);
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }



}
