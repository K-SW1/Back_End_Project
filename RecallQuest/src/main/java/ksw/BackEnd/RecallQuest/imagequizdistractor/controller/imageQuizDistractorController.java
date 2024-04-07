package ksw.BackEnd.RecallQuest.imagequizdistractor.controller;

import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorRequestDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.service.ImageQuizDistractorService;
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
@RequestMapping("/imagequizDistractor")
public class imageQuizDistractorController {

    private final ImageQuizDistractorService imageQuizDistractorService;

    @PostMapping("/save")
    public ResponseEntity<?> createImageQuizDistractor(
            @RequestPart(value="imageQuizDistractorRequestDto", required = true) ImageQuizDistractorRequestDto imageQuizDistractorRequestDto,
            @RequestPart(value="file", required = true) List<MultipartFile> files
    ) throws IOException {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.svaeImageQuizDistractor(imageQuizDistractorRequestDto, files);
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizDistractorResponseDto);
    }

    @GetMapping("/read/content/{content}")
    public ResponseEntity<?> findByDistractorContent(@PathVariable String content) {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.findImageQuizDistractor(content);
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizDistractorResponseDto);
    }

    @GetMapping("/read/all")
    public ResponseEntity<?> findByDistractorContents() {
        List<ImageQuizDistractor> imageQuizDistractor = imageQuizDistractorService.findImageQuizDistractors();
        List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDto = ImageQuizDistractorResponseDto.buildImageQuizDistractorToList(imageQuizDistractor);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizDistractorResponseDto);
    }

    @GetMapping("/read/seq/{seq}")
    public ResponseEntity<?> findBySeq(@PathVariable Long seq) {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.findImageQuizDistractor(seq);

        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizDistractorResponseDto);
    }

    @DeleteMapping("/delete/{imageSeq}")
    public ResponseEntity<?> deleteImageQuizDistractor(@PathVariable Long imageSeq){
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorService.deleteImageQuizDistractor(imageSeq);
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto(imageQuizDistractor);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageQuizDistractorResponseDto);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = imageQuizDistractorService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }


}
