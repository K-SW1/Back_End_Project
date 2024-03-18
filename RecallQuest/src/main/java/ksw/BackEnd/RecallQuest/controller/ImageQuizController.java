package ksw.BackEnd.RecallQuest.controller;

import ksw.BackEnd.RecallQuest.domain.ImageQuiz;
import ksw.BackEnd.RecallQuest.dto.ImageQuizRequestDto;
import ksw.BackEnd.RecallQuest.dto.ImageQuizResponseDto;
import ksw.BackEnd.RecallQuest.service.ImageQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/imagequizs")
public class ImageQuizController {
    private final ImageQuizService imageQuizService;

//    @RequestMapping("/save")
//    public ResponseEntity<ResBodyModel> createImageQuiz(@RequestBody ImageQuizRequestDto imageQuizRequestDto) throws IOException {
//
//        ImageQuiz imageQuiz = imageQuizService.post(imageQuizRequestDto);
//        ImageQuizResponseDto imageQuizResponseDto = ImageQuizResponseDto.buildDto(imageQuiz);
//        return AetResponse.toResponse(SuccessCode.SUCCESS, imageQuizResponseDto);
//    }

    @GetMapping("/items/new")
    public String newItem() {
        return "a";
    }

    @PostMapping ("/items/new")
    public String createImageQuiz(@RequestBody ImageQuizRequestDto imageQuizRequestDto) throws IOException {

        ImageQuiz imageQuiz = imageQuizService.post(imageQuizRequestDto);
        ImageQuizResponseDto imageQuizResponseDto = ImageQuizResponseDto.buildDto(imageQuiz);
        return "ok";
    }

    @RequestMapping(method = { RequestMethod.GET },value = "/multipartdata",produces= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> gerMultipartData()
            throws Exception {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("first_name",  "ganesh");
        formData.add("last_name", "patil");
        formData.add("file-data_1", new FileSystemResource("/Users/gim-yena/Downloads/Shugo chara.jpeg"));
        return new ResponseEntity<MultiValueMap<String, Object>>(formData, HttpStatus.OK);
    }

//    @PostMapping("/api/comments")
//    public CommentRegisterResponse register(@RequestPart (value = "multipartFile") MultipartFile multipartFile) throws IOException {
//
//        int productId = commentService.register(customUserDetails, reservationInfoId, score, comment, multipartFile);
//
//        // response 만들기
//        return CommentRegisterResponse.builder()
//                .result(success)
//                .productId(productId)
//                .build();
//    }


}
