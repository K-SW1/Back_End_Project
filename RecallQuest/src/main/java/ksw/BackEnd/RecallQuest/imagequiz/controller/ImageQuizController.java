package ksw.BackEnd.RecallQuest.imagequiz.controller;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.imagequiz.dto.ImageQuizRequestDto;
import ksw.BackEnd.RecallQuest.imagequiz.dto.ImageQuizResponseDto;
import ksw.BackEnd.RecallQuest.imagequiz.service.ImageQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/imagequiz")
public class ImageQuizController {

    private final ImageQuizService imageQuizService;

//    @PostMapping("/save")
//    public ResponseEntity<?> createImageQuiz(
//            @RequestPart(value="imageQuizRequestDto", required = true) ImageQuizRequestDto imageQuizRequestDto,
//            @RequestPart(value="file", required = true) MultipartFile file
//    ) throws IOException {
//        ImageQuiz imageQuiz = imageQuizService.uploadImageToFileSystem(imageQuizRequestDto, file);
//        ImageQuizResponseDto imageQuizResponseDto = new ImageQuizResponseDto(imageQuiz);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(imageQuizResponseDto);
//    }

    @PostMapping("/save")
    public ResponseEntity<?> createImageQuiz(
            @RequestPart(value="imageQuizRequestDto", required = true) ImageQuizRequestDto imageQuizRequestDto,
            @RequestPart(value="file", required = true) List<MultipartFile> files
    ) throws IOException {
        ImageQuiz imageQuiz = imageQuizService.imageQuizSave(imageQuizRequestDto, files);
        ImageQuizResponseDto imageQuizResponseDto = new ImageQuizResponseDto(imageQuiz);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizResponseDto);
    }

    @GetMapping("/read/question/{question}")
    public ResponseEntity<?> findByQuestion(@PathVariable String question) {
        ImageQuiz imageQuiz = imageQuizService.findImageQuiz(question);
        ImageQuizResponseDto imageQuizResponseDto = new ImageQuizResponseDto(imageQuiz);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizResponseDto);
    }

    @GetMapping("/read/all")
    public ResponseEntity<?> findByImageQuizzes() {
        List<ImageQuiz> imageQuizzes = imageQuizService.findImageQuizzes();
        List<ImageQuizResponseDto> imageQuizResponseDto = ImageQuizResponseDto.buildImageQuizToList(imageQuizzes);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizResponseDto);
    }

    @GetMapping("/read/all/distractor/{seq}")
    public ResponseEntity<?> findByImageQuizzesAndDistractor(@PathVariable Long seq) {
        ImageQuiz imageQuiz = imageQuizService.findQuiz(seq);
//        List<ImageQuizDistractor> imageQuizDistractors = imageQuizService.getImageQuizDistractorsByImageQuizId(seq);
//        List<ImageQuizAndDistractorResponseDto> imageQuizResponseDto = ImageQuizAndDistractorResponseDto.buildImageQuizToList(imageQuizDistractors);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuiz);
    }

    @GetMapping("/read/seq/{seq}")
    public ResponseEntity<?> findBySeq(@PathVariable Long seq) {
        ImageQuiz imageQuiz = imageQuizService.findImageQuiz(seq);
        ImageQuizResponseDto imageQuizResponseDto = new ImageQuizResponseDto(imageQuiz);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizResponseDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateImageQuiz(
            @RequestPart(value="imageQuizRequestDto", required = true) ImageQuizRequestDto imageQuizRequestDto,
            @RequestPart(value="file", required = true) List<MultipartFile> files
    ) throws IOException {
        ImageQuiz imageQuiz = imageQuizService.updateImageQuiz(imageQuizRequestDto, files);
        ImageQuizResponseDto imageQuizResponseDto = new ImageQuizResponseDto(imageQuiz);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizResponseDto);
    }

    @DeleteMapping("/delete/{imageSeq}")
    public ResponseEntity<?> deleteImageQuiz(@PathVariable Long imageSeq){
        ImageQuiz imageQuiz = imageQuizService.deleteImageQuiz(imageSeq);
        ImageQuizResponseDto imageQuizResponseDto = new ImageQuizResponseDto(imageQuiz);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizResponseDto);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = imageQuizService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

}
