package ksw.BackEnd.RecallQuest.Textquiz.controller;


import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import ksw.BackEnd.RecallQuest.Textquiz.service.TextQuizService;
import ksw.BackEnd.RecallQuest.jwt.dto.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import java.util.List;

import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizRequestDto;
import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizResponseDto;


import java.util.stream.Collectors;

import ksw.BackEnd.RecallQuest.common.KsResponse;
import ksw.BackEnd.RecallQuest.common.code.SuccessCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;


@RestController
@RequiredArgsConstructor
@RequestMapping("/textquiz")
public class TextQuizController {

    private final TextQuizService textQuizService;


    /**
     *추가 서비스
     */
    // TextQuiz 문제랑 힌트 추가  + AetResponse 변경 완료 (단일)
    @PostMapping("/add")
    public ResponseEntity<ResBodyModel> addTextQuiz(
            @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody TextQuizRequestDto requestDto) {

        requestDto.setUserLoginId(customUserDetails.getUsername());

        // 요청 DTO를 엔티티로 변환합니다
        TextQuizResponseDto responseDto = textQuizService.addTextQuiz(requestDto);

        // 적절한 상태와 함께 응답 DTO를 반환합니다
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }





    /**
     *조회 서비스
     */
    // TextQuiz 문제랑 힌트 조회 + AetResponse 변경 완료 (전체)
    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> getAllTextQuizzes() {
        List<TextQuiz> textQuizzes = textQuizService.getAllTextQuizzes();

        List<TextQuizResponseDto> responseDtos = textQuizzes.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        // AetResponse를 사용하여 ResponseEntity를 생성
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }
    private TextQuizResponseDto convertToResponseDto(TextQuiz textQuiz) {
        TextQuizResponseDto responseDto = new TextQuizResponseDto();
        responseDto.setTextQuizId(textQuiz.getTextQuizId());
        responseDto.setMember(  textQuiz.getMember());
        responseDto.setQuestion(textQuiz.getQuestion());
        responseDto.setHint(textQuiz.getHint());
        return responseDto;
    }


    // TextQuiz 문제랑 힌트 특정 조회 (단일)
    @GetMapping("/{textQuizId}")
    public ResponseEntity<ResBodyModel> getTextQuizById(@PathVariable int textQuizId) {
        TextQuizResponseDto textQuiz = textQuizService.getTextQuizById(textQuizId);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return KsResponse.toResponse(SuccessCode.SUCCESS, textQuiz);
    }



    /**
     *수정 서비스
     */
    // TextQuiz 문제랑 힌트 수정 컨트롤러 + AetResponse 변경 완료 (단일)
    @PutMapping("/{textQuizId}/update")
    public ResponseEntity<ResBodyModel> updateTextQuiz(@PathVariable int textQuizId,
                                                       @RequestBody TextQuizRequestDto updatedTextQuizRequestDto) {
        TextQuizResponseDto updatedQuiz = textQuizService.updateTextQuiz(textQuizId, updatedTextQuizRequestDto);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return KsResponse.toResponse(SuccessCode.SUCCESS, updatedQuiz);
    }





    /**
     *삭제 서비스
     */
    // TextQuiz 및 TextChoice 한번에 삭제 + AetResponse 변경 완료 (단일)
    @DeleteMapping("/{textQuizId}/delete")
    public ResponseEntity<ResBodyModel> deleteTextQuiz(@PathVariable("textQuizId") int textQuizId) {
        // 텍스트 퀴즈 삭제 서비스 호출
        textQuizService.deleteTextQuiz(textQuizId);

        // 삭제 성공을 응답으로 반환
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }



}
