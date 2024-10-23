package ksw.BackEnd.RecallQuest.Imagequiz.controller;

import ksw.BackEnd.RecallQuest.Imagequiz.dto.ImageQuizRequestDto;
import ksw.BackEnd.RecallQuest.Imagequiz.dto.ImageQuizResponseDto;
import ksw.BackEnd.RecallQuest.Imagequiz.dto.ImageQuizWithDistractorsResponseDto;
import ksw.BackEnd.RecallQuest.Imagequiz.mapper.ImageQuizMapper;
import ksw.BackEnd.RecallQuest.Imagequiz.service.ImageQuizService;
import ksw.BackEnd.RecallQuest.ImagequizDistractor.service.ImageDistractorService;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.jwt.dto.CustomUserDetails;
import ksw.BackEnd.RecallQuest.common.KsResponse;
import ksw.BackEnd.RecallQuest.common.code.SuccessCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/imagequiz")
public class ImageQuizController {

    private final ImageQuizService imageQuizService;
    private final ImageQuizMapper imageQuizMapper;
    private final ImageQuizMapper imageDistractorMapper;
    private final ImageDistractorService imageDistractorService;


    /**
     * 이미지 퀴즈 저장
     */
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResBodyModel> addImageQuiz(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart("imageQuiz") ImageQuizRequestDto requestDto,  // JSON 데이터
            @RequestPart("image") MultipartFile imageFile              // 이미지 파일
    ) throws IOException {
        requestDto.setUserLoginId(customUserDetails.getUsername());

        // 이미지 저장 처리 (예: 파일 시스템에 저장하거나, 이미지 URL 생성 등)
        String imageUrl = imageQuizService.saveImage(imageFile);  // 이미지 저장 로직 추가

        // 이미지 URL을 requestDto에 설정
        requestDto.setImageUrl(imageUrl);

        ImageQuiz savedImageQuiz = imageQuizService.addImageQuiz(requestDto);
        ImageQuizResponseDto responseDto = imageQuizMapper.toDto(savedImageQuiz);

        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }


    /**
     * 이미지 퀴즈 수정
     */
    @PutMapping(value = "/{imageQuizId}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResBodyModel> updateImageQuiz(
            @PathVariable int imageQuizId,
            @RequestPart("imageQuiz") ImageQuizRequestDto updatedImageQuizRequestDto, // JSON 데이터
            @RequestPart(value = "image", required = false) MultipartFile imageFile // 이미지 파일 (선택적)
    ) throws IOException, IllegalAccessException {
        // 이미지 퀴즈 업데이트 처리
        ImageQuiz updatedImageQuiz = imageQuizService.updateImageQuiz(imageQuizId, updatedImageQuizRequestDto, imageFile);
        ImageQuizResponseDto responseDto = imageQuizMapper.toDto(updatedImageQuiz);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }



    /**
     * 이미지 퀴즈 조회
     */
    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> getAllImageQuizzes() {
        List<ImageQuiz> imageQuizzes = imageQuizService.getAllImageQuizzes();
        List<ImageQuizResponseDto> responseDtos = imageQuizMapper.toDtoList(imageQuizzes);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }

    // 이미지 퀴즈 단일 조회
    @GetMapping("/{imageQuizId}")
    public ResponseEntity<ResBodyModel> getImageQuizById(@PathVariable int imageQuizId) throws IllegalAccessException {
        ImageQuiz imageQuiz = imageQuizService.getImageQuizById(imageQuizId);
        ImageQuizResponseDto responseDto = imageQuizMapper.toDto(imageQuiz);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }

    /**
     * 이미지 퀴즈 및 보기 정답 조회
     */
    @GetMapping("/{imageQuizId}/details")
    public ResponseEntity<ResBodyModel> getImageQuizWithDistractors(@PathVariable int imageQuizId) throws IllegalAccessException {
        ImageQuiz imageQuiz = imageDistractorService.getImageQuizWithDistractors(imageQuizId);
        ImageQuizWithDistractorsResponseDto responseDto = imageDistractorMapper.toResponseDto(imageQuiz);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }

    /**
     * 이미지 퀴즈 삭제
     */
    @DeleteMapping("/{imageQuizId}/delete")
    public ResponseEntity<ResBodyModel> deleteImageQuiz(@PathVariable("imageQuizId") int imageQuizId) throws IOException, IllegalAccessException {
        imageQuizService.deleteImageQuiz(imageQuizId);
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }
}
