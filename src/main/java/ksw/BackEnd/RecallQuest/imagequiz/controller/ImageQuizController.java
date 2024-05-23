package ksw.BackEnd.RecallQuest.imagequiz.controller;

import ksw.BackEnd.RecallQuest.common.KsResponse;
import ksw.BackEnd.RecallQuest.common.code.SuccessCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import ksw.BackEnd.RecallQuest.entity.Login;
import ksw.BackEnd.RecallQuest.imagequiz.dto.*;

import ksw.BackEnd.RecallQuest.imagequiz.mapper.CompleteMapper;
import ksw.BackEnd.RecallQuest.imagequiz.mapper.QuizMapper;
import ksw.BackEnd.RecallQuest.imagequiz.service.ImageQuizService;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto;
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
@RequestMapping("/imagequiz")
@Slf4j
public class ImageQuizController {

    private final ImageQuizService imageQuizService;
    private final QuizMapper quizMapper;

    private final ImageQuizDistractorService imageQuizDistractorService;
    private final CompleteMapper completeMapper;


    /**
     *이미지 퀴즈 저장
     */
    @PostMapping("/save")
    public ResponseEntity<ResBodyModel> createImageQuiz(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart(value="imageQuizRequestDto") ImageQuizRequestDto imageQuizRequestDto,
            @RequestPart(value="file", required = false) List<MultipartFile> files
    ) throws IOException {
        imageQuizRequestDto.setUserLoginId(customUserDetails.getUsername());
        ImageQuiz imageQuiz = imageQuizService.imageQuizSave(imageQuizRequestDto, files);
        List<Map<String, Object>> imageList = quizMapper.quizPhotoMapping(imageQuiz.getQuestionImages());
        ImageQuizResponseDto imageQuizResponseDto = new ImageQuizResponseDto(imageQuiz, imageList);
        return KsResponse.toResponse(SuccessCode.SUCCESS, imageQuizResponseDto);
    }


    /**
     *이미지 퀴즈 수정
     */
    @PatchMapping("/update")
    public ResponseEntity<ResBodyModel> updateImageQuiz(
            @RequestPart(value="updateRequestDto") UpdateRequestDto updateRequestDto,
            @RequestPart(value="file", required = false) List<MultipartFile> files
    ) throws IOException {
        ImageQuiz imageQuiz = imageQuizService.updateImageQuiz(updateRequestDto, files);
        List<Map<String, Object>> imageList = quizMapper.quizPhotoMapping(imageQuiz.getQuestionImages());
        ImageQuizResponseDto imageQuizResponseDto = new ImageQuizResponseDto(imageQuiz, imageList);
        return KsResponse.toResponse(SuccessCode.SUCCESS, imageQuizResponseDto);
    }

    /**
     *이미지 퀴즈 조회
     */
    //문제보기 단일 조회 - 문제 내용으로 검색
    @GetMapping("/read/question")
    public ResponseEntity<ResBodyModel> findByQuestion(@RequestBody ImageQuizReadDto imageQuizReadDto) throws IOException {
        ImageQuiz imageQuiz = imageQuizService.findImageQuiz(imageQuizReadDto);
        List<Map<String, Object>> imageList = quizMapper.quizPhotoMapping(imageQuiz.getQuestionImages());
        ImageQuizResponseDto imageQuizResponseDto = new ImageQuizResponseDto(imageQuiz, imageList);
        return KsResponse.toResponse(SuccessCode.SUCCESS, imageQuizResponseDto);
    }

    //문제보기 단일 조회 - 문제랑 이미지만 나오게
    @GetMapping("/read/seq/{seq}")
    public ResponseEntity<ResBodyModel> findBySeq(@PathVariable Long seq) throws IOException {
        ImageQuiz imageQuiz = imageQuizService.findImageQuiz(seq);
        List<Map<String, Object>> imageList = quizMapper.quizPhotoMapping(imageQuiz.getQuestionImages());
        ImageQuizResponseDto imageQuizResponseDto = new ImageQuizResponseDto(imageQuiz, imageList);
        return KsResponse.toResponse(SuccessCode.SUCCESS, imageQuizResponseDto);
    }

    //문제보기 모두 조회 - 문제랑 이미지만 나오게
    @GetMapping("/read/all")
    public ResponseEntity<ResBodyModel> findByImageQuizzes() throws IOException {
        List<ImageQuiz> ImageQuizList = imageQuizService.findImageQuizzes();
        List<ImageQuizResponseDto> imageQuizResponseDtoList = quizMapper.toResponse(ImageQuizList);
        return KsResponse.toResponse(SuccessCode.SUCCESS, imageQuizResponseDtoList);
    }

    //문제보기 단일 조회 - 문제와 보기가 함께 나오게
    @GetMapping("/read/complete/{seq}")
    public ResponseEntity<ResBodyModel> findByImageQuizzesAndDistractor(@PathVariable Long seq) throws IOException {
//        ImageQuiz imageQuiz = imageQuizService.findImageQuiz(seq);
//        List<Map<String, Object>> imageList = quizMapper.quizPhotoMapping(imageQuiz.getQuestionImages());
//
//        List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorService.findByImageQuizId(seq);
//        List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtoList = distractorMapper.toResponse(imageQuizDistractors);
//
//        CompleteImageQuizResponseDto completeImageQuizResponseDto = new CompleteImageQuizResponseDto(imageQuiz, imageList, imageQuizDistractorResponseDtoList);

        ImageQuiz imageQuiz = imageQuizService.findImageQuiz(seq);
        List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorService.findByImageQuizId(seq);
        CompleteImageQuizResponseDto completeImageQuizResponseDto = completeMapper.toCompleteResponse(imageQuiz, imageQuizDistractors);

        return KsResponse.toResponse(SuccessCode.SUCCESS, completeImageQuizResponseDto);
    }

    //문제보기 전체 조회 - 문제와 보기가 함께 나오게
    @GetMapping("/read/complete/all")
    public ResponseEntity<ResBodyModel> findAllByImageQuizzesAndDistractor() throws IOException {
        List<CompleteImageQuizResponseDto> completeImageQuizResponseDtoList = new ArrayList<>();
        List<ImageQuiz> ImageQuizList = imageQuizService.findImageQuizzes();

        //퀴즈 번호 부여
        Long quizNumber = 1L;
        for (ImageQuiz imageQuiz : ImageQuizList) {
            List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorService.findByImageQuizId(imageQuiz.getImageQuizSeq());
            CompleteImageQuizResponseDto completeImageQuizResponseDto = completeMapper.toCompleteResponse(imageQuiz, imageQuizDistractors);
            completeImageQuizResponseDto.setImageQuizSeq(quizNumber++);
            completeImageQuizResponseDtoList.add(completeImageQuizResponseDto);
        }

        return KsResponse.toResponse(SuccessCode.SUCCESS, completeImageQuizResponseDtoList);
    }

    //문제보기 전체 조회 - 문제와 보기가 함께 나오게, 회원 시퀀스로 검색
    @GetMapping("/read/complete/member/{memberSeq}")
    public ResponseEntity<ResBodyModel> findAllByImageQuizzesAndDistractors(@PathVariable Long memberSeq) throws IOException {
        List<CompleteImageQuizResponseDto> completeImageQuizResponseDtoList = new ArrayList<>();
        List<ImageQuiz> ImageQuizList = imageQuizService.findImageQuizzes(memberSeq);

        //퀴즈 번호 부여
        Long quizNumber = 1L;
        for (ImageQuiz imageQuiz : ImageQuizList) {
            List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorService.findByImageQuizId(imageQuiz.getImageQuizSeq());
            CompleteImageQuizResponseDto completeImageQuizResponseDto = completeMapper.toCompleteResponse(imageQuiz, imageQuizDistractors);
            completeImageQuizResponseDto.setImageQuizSeq(quizNumber++);
            completeImageQuizResponseDtoList.add(completeImageQuizResponseDto);
        }

        return KsResponse.toResponse(SuccessCode.SUCCESS, completeImageQuizResponseDtoList);
    }

//    //문제보기 전체 조회 - 문제와 보기가 함께 나오게, 회원 로그인아이디로 검색
//    @GetMapping("/read/complete/loginId/{loginId}")
//    public ResponseEntity<ResBodyModel> findAllByLoginId(@PathVariable String loginId) throws IOException {
//        List<CompleteImageQuizResponseDto> completeImageQuizResponseDtoList = new ArrayList<>();
//        List<ImageQuiz> ImageQuizList = imageQuizService.findImageQuizzes(loginId);
//
//        //퀴즈 번호 부여
//        Long quizNumber = 1L;
//        for (ImageQuiz imageQuiz : ImageQuizList) {
//            List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorService.findByImageQuizId(imageQuiz.getImageQuizSeq());
//            CompleteImageQuizResponseDto completeImageQuizResponseDto = completeMapper.toCompleteResponse(imageQuiz, imageQuizDistractors);
//            completeImageQuizResponseDto.setImageQuizSeq(quizNumber++);
//            completeImageQuizResponseDtoList.add(completeImageQuizResponseDto);
//        }
//
//        return KsResponse.toResponse(SuccessCode.SUCCESS, completeImageQuizResponseDtoList);
//    }

    //문제보기 전체 조회 - 문제와 보기가 함께 나오게, 회원 로그인아이디로
    @GetMapping("/read/complete/loginId")
    public ResponseEntity<ResBodyModel> findAllByUserLoginId(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {
        List<CompleteImageQuizResponseDto> completeImageQuizResponseDtoList = new ArrayList<>();
        List<ImageQuiz> ImageQuizList = imageQuizService.findImageQuizzes(customUserDetails.getUsername());

        //퀴즈 번호 부여
        Long quizNumber = 1L;
        for (ImageQuiz imageQuiz : ImageQuizList) {
            List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorService.findByImageQuizId(imageQuiz.getImageQuizSeq());
            CompleteImageQuizResponseDto completeImageQuizResponseDto = completeMapper.toCompleteResponse(imageQuiz, imageQuizDistractors);
            completeImageQuizResponseDto.setImageQuizSeq(quizNumber++);
            completeImageQuizResponseDtoList.add(completeImageQuizResponseDto);
        }

        return KsResponse.toResponse(SuccessCode.SUCCESS, completeImageQuizResponseDtoList);
    }

    /**
     *이미지 퀴즈 삭제
     */
    @DeleteMapping("/delete/{imageSeq}")
    public ResponseEntity<ResBodyModel> deleteImageQuiz(@PathVariable Long imageSeq) throws IOException {
        imageQuizService.deleteImageQuiz(imageSeq);
        return KsResponse.toResponse(SuccessCode.SUCCESS);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResBodyModel> deleteImageQuizByQuestion(@RequestBody ImageQuizReadDto imageQuizReadDto) throws IOException {
        imageQuizService.deleteImageQuizByQuestion(imageQuizReadDto);
        return KsResponse.toResponse(SuccessCode.SUCCESS);
    }

}
