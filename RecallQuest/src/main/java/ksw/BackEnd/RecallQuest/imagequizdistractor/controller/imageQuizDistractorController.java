package ksw.BackEnd.RecallQuest.imagequizdistractor.controller;

import ksw.BackEnd.RecallQuest.entity.DistractorImage;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.DistractorReadDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorRequestDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.UpdateRequestDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.mapper.DistractorMapper;
import ksw.BackEnd.RecallQuest.imagequizdistractor.service.ImageQuizDistractorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/save")
    public ResponseEntity<?> createImageQuizDistractor(
            @RequestPart(value="imageQuizDistractorRequestDto") ImageQuizDistractorRequestDto imageQuizDistractorRequestDto,
            @RequestPart(value="file", required = false) List<MultipartFile> files
    ) throws IOException {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.svaeImageQuizDistractor(imageQuizDistractorRequestDto, files);
        List<Map<String, Object>> imageList = distractorMapper.distractorPhotoMapping(imageQuizDistractor.getDistractorImages());
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor, imageList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizDistractorResponseDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateImageQuizDistractor(
            @RequestPart(value="updateRequestDto") UpdateRequestDto updateRequestDto,
            @RequestPart(value="file", required = false) List<MultipartFile> files
    ) throws IOException {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.updateImageQuizDistractor(updateRequestDto, files);
        List<Map<String, Object>> imageList = distractorMapper.distractorPhotoMapping(imageQuizDistractor.getDistractorImages());
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor, imageList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizDistractorResponseDto);
    }

//
//    @GetMapping("/read/content/{content}")
//    public ResponseEntity<?> findByDistractorContent(@PathVariable String content) {
//        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.findImageQuizDistractor(content);
//        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(imageQuizDistractorResponseDto);
//    }


    //보기 전체 조회 - 보기랑 이미지만 나오게
    @GetMapping("/read/all")
    public ResponseEntity<?> findByDistractorContents() throws IOException {

        List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorService.findImageQuizDistractors();

        List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtoList = distractorMapper.toResponse(imageQuizDistractors);
        return ResponseEntity.status(HttpStatus.OK).body(imageQuizDistractorResponseDtoList);
    }

    //보기 단일 조회 - 보기랑 이미지만 나오게, 시퀀스로 조회
    @GetMapping("/read/seq/{seq}")
    public ResponseEntity<?> findBySeq(@PathVariable Long seq) throws IOException {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.findImageQuizDistractor(seq);
        List<Map<String, Object>> imageList = distractorMapper.distractorPhotoMapping(imageQuizDistractor.getDistractorImages());
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor, imageList);
        log.info("응답객체 = {}", imageQuizDistractorResponseDto.getImageQuizDistractor());
        return ResponseEntity.status(HttpStatus.OK).body(imageQuizDistractorResponseDto);
    }

    //보기 단일 조회 - 보기랑 이미지만 나오게, 내용으로 조회
    @GetMapping("/read/distractor")
    public ResponseEntity<?> findByContent(@RequestBody DistractorReadDto distractorReadDto) throws IOException {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.findImageQuizDistractor(distractorReadDto);
        List<Map<String, Object>> imageList = distractorMapper.distractorPhotoMapping(imageQuizDistractor.getDistractorImages());
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor, imageList);
        log.info("응답객체 = {}", imageQuizDistractorResponseDto.getImageQuizDistractor());
        return ResponseEntity.status(HttpStatus.OK).body(imageQuizDistractorResponseDto);
    }

    @DeleteMapping("/delete/{distractorSeq}")
    public ResponseEntity<?> deleteImageQuizDistractor(@PathVariable Long distractorSeq) throws IOException {
        imageQuizDistractorService.deleteImageQuizDistractor(distractorSeq);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteImageQuizDistractor(@RequestBody DistractorReadDto distractorReadDto) throws IOException {
        imageQuizDistractorService.deleteImageQuizDistractor(distractorReadDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @GetMapping(value = "/{fileName}")
//    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
//        byte[] imageData = imageQuizDistractorService.downloadImageFromFileSystem(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(imageData);
//    }


}
