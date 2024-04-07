package ksw.BackEnd.RecallQuest.imagequizdistractor.service;

import ksw.BackEnd.RecallQuest.DataNotFoundException;
import ksw.BackEnd.RecallQuest.entity.*;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorRequestDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.repository.DistractorImageRepository;
import ksw.BackEnd.RecallQuest.imagequizdistractor.repository.ImageQuizDistractorRepository;
import ksw.BackEnd.RecallQuest.imagequiz.repository.ImageQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageQuizDistractorService {

    private final ImageQuizDistractorRepository imageQuizDistractorRepository;
    private final DistractorImageRepository distractorImageRepository;
    private final ImageQuizRepository imageQuizRepository;

    private final String FOLDER_PATH="/Users/gim-yena/Desktop/imageDistractor/";

    /**
     * 선택지문제와 선택지 이미지 함께 저장
     */
    public ImageQuizDistractor svaeImageQuizDistractor(ImageQuizDistractorRequestDto imageQuizDistractorRequestDto, List<MultipartFile> files) throws IOException {

        ImageQuiz imageQuiz = imageQuizRepository.findById(imageQuizDistractorRequestDto.getImageQuizId()).orElseThrow(() -> new DataNotFoundException("회원을 찾을 수 없습니다."));

        ImageQuizDistractor imageQuizDistractor = ImageQuizDistractor.builder()
                .imageQuizDistractor(imageQuizDistractorRequestDto.getImageQuizDistractor())
                .imageQuiz(imageQuiz)
                .validation(imageQuizDistractorRequestDto.isValidation())
                .build();

        imageQuizDistractorRepository.save(imageQuizDistractor);

        for (MultipartFile file : files) {

            String filePath = FOLDER_PATH + file.getOriginalFilename();
            String storeFilename = changedImageName(file.getOriginalFilename());

            DistractorImage distractorImage = DistractorImage.builder()
                    .originFilename(file.getOriginalFilename())
                    .storeFilename(storeFilename)
                    .type(file.getContentType())
                    .filePath(filePath)
                    .imageQuizDistractor(imageQuizDistractor)
                    .build();

            // 이미지 퀴즈에 이미지 정보 추가
            imageQuizDistractor.getDistractorImages().add(distractorImage);

            distractorImageRepository.save(distractorImage);

            file.transferTo(new File(filePath));

        }

        return imageQuizDistractor;

    }

    /*
     * 선택지 조회
     */
    public ImageQuizDistractor findImageQuizDistractor(String distractorContent) {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorRepository.findByImageQuizDistractor(distractorContent);
        return imageQuizDistractor;
    }

    public ImageQuizDistractor findImageQuizDistractor(Long imageQuizDistractorSeq) {
        Optional<ImageQuizDistractor> imageQuizDistractor = imageQuizDistractorRepository.findById(imageQuizDistractorSeq);
        return imageQuizDistractor.orElseThrow(() -> new DataNotFoundException("존재하지 않는 이미지 퀴즈 번호 입니다."));
    }

    public List<ImageQuizDistractor> findImageQuizDistractors() {
        List<ImageQuizDistractor> imageQuizDistractors = imageQuizDistractorRepository.findAll();
        return imageQuizDistractors;
    }

    /*
     * 이미지 퀴즈 선택지 수정, 퀴즈 선택지 내용으로 조회해 옴
     */
    public ImageQuizDistractor updateImageQuizDistractor(ImageQuizDistractorRequestDto imageQuizDistractorRequestDto, List<MultipartFile> files) throws IOException {

        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorRepository.findByImageQuizDistractor(imageQuizDistractorRequestDto.getImageQuizDistractor());
        imageQuizDistractor.changeInfo(imageQuizDistractorRequestDto);

        imageQuizDistractorRepository.save(imageQuizDistractor);

        // 기존에 연결된 이미지 정보를 모두 삭제
        imageQuizDistractor.getDistractorImages().clear();
        distractorImageRepository.deleteByImageQuizDistractor(imageQuizDistractor);

        for (MultipartFile file : files) {

            String filePath = FOLDER_PATH + file.getOriginalFilename();
            String storeFilename = changedImageName(file.getOriginalFilename());

            DistractorImage distractorImage = DistractorImage.builder()
                    .originFilename(file.getOriginalFilename())
                    .storeFilename(storeFilename)
                    .type(file.getContentType())
                    .filePath(filePath)
                    .imageQuizDistractor(imageQuizDistractor)
                    .build();

            // 이미지 퀴즈에 이미지 정보 추가
            imageQuizDistractor.getDistractorImages().add(distractorImage);
            distractorImageRepository.save(distractorImage);

            file.transferTo(new File(filePath));
        }

        return imageQuizDistractor;
    }

    /*
    이미지 퀴즈 삭제
     */
    public ImageQuizDistractor deleteImageQuizDistractor(Long imageQuizDistractorSeq) {
        ImageQuizDistractor imageQuizDistractor = imageQuizDistractorRepository.findById(imageQuizDistractorSeq).orElseThrow(() -> new DataNotFoundException("존재하지 않는 이미지 퀴즈 번호 입니다."));
        imageQuizDistractorRepository.deleteById(imageQuizDistractorSeq);
        return imageQuizDistractor;
    }

    //이미지 불러오기
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<DistractorImage> distractorImage = distractorImageRepository.findByOriginFilename(fileName);
        String filePath = distractorImage.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    private static String changedImageName(String originName) {
        String random = UUID.randomUUID().toString();
        return random+originName;
    }
}
