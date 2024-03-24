package ksw.BackEnd.RecallQuest.kkk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private StorageService service;

@PostMapping("/save")
public ResponseEntity<?> createImageQuiz(
        @RequestPart(value="question", required = true) String question,
        @RequestPart(value="file", required = true) MultipartFile file
) throws IOException {

    String uploadImage = service.uploadImageToFileSystem(question, file);
    return ResponseEntity.status(HttpStatus.OK)
            .body(uploadImage);

}

    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData=service.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }
}
