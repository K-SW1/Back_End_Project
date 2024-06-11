package ksw.BackEnd.RecallQuest.imagequizdistractor.controller;

import ksw.BackEnd.RecallQuest.common.KsResponse;
import ksw.BackEnd.RecallQuest.common.code.SuccessCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;
import ksw.BackEnd.RecallQuest.entity.DistractorImage;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.DistractorReadDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorRequestDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.UpdateRequestDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.mapper.DistractorMapper;
import ksw.BackEnd.RecallQuest.imagequizdistractor.service.ImageQuizDistractorService;
import ksw.BackEnd.RecallQuest.jwt.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/imagequizDistractor")
@Slf4j
public class imageQuizDistractorController {

    private final ImageQuizDistractorService imageQuizDistractorService;
    private final DistractorMapper distractorMapper;

    /*
    보기 저장
     */
    @PostMapping("/save")
    public ResponseEntity<ResBodyModel> createImageQuizDistractor(
            @RequestPart(value="imageQuizDistractorRequestDto") ImageQuizDistractorRequestDto imageQuizDistractorRequestDto,
            @RequestPart(value="file", required = false) List<MultipartFile> files
    ) throws IOException {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.svaeImageQuizDistractor(imageQuizDistractorRequestDto, files);
        List<Map<String, Object>> imageList = distractorMapper.distractorPhotoMapping(imageQuizDistractor.getDistractorImages());
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor, imageList);
        return KsResponse.toResponse(SuccessCode.SUCCESS, imageQuizDistractorResponseDto);
    }

    /*
    보기 수정
     */
    @PatchMapping("/update")
    public ResponseEntity<ResBodyModel> updateImageQuizDistractor(
            @RequestPart(value="updateRequestDto") UpdateRequestDto updateRequestDto,
            @RequestPart(value="file", required = false) List<MultipartFile> files
    ) throws IOException {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.updateImageQuizDistractor(updateRequestDto, files);
        List<Map<String, Object>> imageList = distractorMapper.distractorPhotoMapping(imageQuizDistractor.getDistractorImages());
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor, imageList);
        return KsResponse.toResponse(SuccessCode.SUCCESS, imageQuizDistractorResponseDto);
    }

    //보기 전체 조회 - 보기랑 이미지만 나오게
    @GetMapping("/read/all")
    public ResponseEntity<ResBodyModel> findByDistractorContents() throws IOException {

        List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorService.findImageQuizDistractors();

        List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtoList = distractorMapper.toResponse(imageQuizDistractors);
        return KsResponse.toResponse(SuccessCode.SUCCESS, imageQuizDistractorResponseDtoList);
    }

    //보기 단일 조회 - 보기랑 이미지만 나오게, 시퀀스로 조회
    @GetMapping("/read/seq/{seq}")
    public ResponseEntity<ResBodyModel> findBySeq(@PathVariable Long seq) throws IOException {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.findImageQuizDistractor(seq);
        List<Map<String, Object>> imageList = distractorMapper.distractorPhotoMapping(imageQuizDistractor.getDistractorImages());
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor, imageList);
        return KsResponse.toResponse(SuccessCode.SUCCESS, imageQuizDistractorResponseDto);
    }

    //보기 단일 조회 - 보기랑 이미지만 나오게, 내용으로 조회
    @GetMapping("/read/distractor")
    public ResponseEntity<ResBodyModel> findByContent(@RequestBody DistractorReadDto distractorReadDto) throws IOException {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.findImageQuizDistractor(distractorReadDto);
        List<Map<String, Object>> imageList = distractorMapper.distractorPhotoMapping(imageQuizDistractor.getDistractorImages());
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor, imageList);
        return KsResponse.toResponse(SuccessCode.SUCCESS, imageQuizDistractorResponseDto);
    }

    /*
    보기 삭제
     */
    @DeleteMapping("/delete/{distractorSeq}")
    public ResponseEntity<ResBodyModel> deleteImageQuizDistractor(@PathVariable Long distractorSeq) throws IOException {
        imageQuizDistractorService.deleteImageQuizDistractor(distractorSeq);
        return KsResponse.toResponse(SuccessCode.SUCCESS);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResBodyModel> deleteImageQuizDistractor(@RequestBody DistractorReadDto distractorReadDto) throws IOException {
        imageQuizDistractorService.deleteImageQuizDistractor(distractorReadDto);
        return KsResponse.toResponse(SuccessCode.SUCCESS);
    }


}
