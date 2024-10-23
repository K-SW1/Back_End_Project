package ksw.BackEnd.RecallQuest.ImagequizDistractor.controller;

import ksw.BackEnd.RecallQuest.Imagequiz.dto.ImageQuizWithDistractorsResponseDto;
import ksw.BackEnd.RecallQuest.ImagequizDistractor.dto.ImageDistractorRequestDto;
import ksw.BackEnd.RecallQuest.ImagequizDistractor.dto.ImageDistractorResponseDto;
import ksw.BackEnd.RecallQuest.ImagequizDistractor.mapper.ImageDistractorMapper;
import ksw.BackEnd.RecallQuest.ImagequizDistractor.service.ImageDistractorService;
import ksw.BackEnd.RecallQuest.common.KsResponse;
import ksw.BackEnd.RecallQuest.common.code.SuccessCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;
import ksw.BackEnd.RecallQuest.entity.ImagequizDistractor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/imagequizDistractor")
public class ImageDistractorController {

    private final ImageDistractorService imageDistractorService;
    private final ImageDistractorMapper imageDistractorMapper;

    /**
     * 추가 서비스
     */
    // 이미지 선택지 추가
    @PostMapping("/{imageQuizId}/distractor/add")
    public ResponseEntity<ResBodyModel> addImageDistractorToQuiz(
            @PathVariable("imageQuizId") int imageQuizId,
            @RequestBody ImageDistractorRequestDto distractorRequestDto
    ) throws IOException {
        ImagequizDistractor savedDistractor = imageDistractorService.addImageDistractorToQuiz(imageQuizId, distractorRequestDto);
        ImageDistractorResponseDto responseDto = imageDistractorMapper.toResponse(savedDistractor);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }

    // 이미지 선택지 리스트 추가
    @PostMapping("/{imageQuizId}/distractors/add")
    public ResponseEntity<ResBodyModel> addImageDistractorsToQuiz(
            @PathVariable("imageQuizId") int imageQuizId,
            @RequestBody List<ImageDistractorRequestDto> distractors
    ) throws IOException {
        List<ImagequizDistractor> savedDistractors = imageDistractorService.addImageDistractorsToQuiz(imageQuizId, distractors);
        List<ImageDistractorResponseDto> responseDtos = imageDistractorMapper.toResponseDistractorList(savedDistractors);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }

    /**
     * 조회 서비스
     */
    // 이미지 선택지 조회
    @GetMapping("/{imageQuizId}/distractors")
    public ResponseEntity<ResBodyModel> getImageDistractorsByQuizId(@PathVariable("imageQuizId") int imageQuizId) {
        List<ImagequizDistractor> imageDistractors = imageDistractorService.getImageDistractorsByQuizId(imageQuizId);
        List<ImageDistractorResponseDto> responseDtos = imageDistractorMapper.toResponseDistractorList(imageDistractors);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }

    /**
     * 수정 서비스
     */
    // 이미지 선택지 수정
    @PutMapping("/{imageQuizId}/distractors/update")
    public ResponseEntity<ResBodyModel> updateImageDistractors(
            @PathVariable int imageQuizId,
            @RequestBody List<ImageDistractorRequestDto> updatedDistractorsRequestDto) {
        List<ImagequizDistractor> updatedDistractors = imageDistractorService.updateImageDistractors(imageQuizId, updatedDistractorsRequestDto);
        List<ImageDistractorResponseDto> updatedDistractorsResponseDto = imageDistractorMapper.toResponseDistractorList(updatedDistractors);
        return KsResponse.toResponse(SuccessCode.SUCCESS, updatedDistractorsResponseDto);
    }

    /**
     * 삭제 서비스
     */
    // 이미지 선택지 삭제
    @DeleteMapping("/distractor/{imageDistractorId}/delete")
    public ResponseEntity<ResBodyModel> deleteImageDistractor(@PathVariable int imageDistractorId) {
        imageDistractorService.deleteImageDistractor(imageDistractorId);
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }

    // 이미지 선택지 전체 삭제
    @DeleteMapping("/imagequiz/{imageQuizId}/distractors/delete")
    public ResponseEntity<ResBodyModel> deleteAllDistractorsByImageQuizId(@PathVariable int imageQuizId) {
        imageDistractorService.deleteAllDistractorsByImageQuizId(imageQuizId);
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }
}
