package ksw.BackEnd.RecallQuest.imagequiz.service;

import ksw.BackEnd.RecallQuest.DataNotFoundException;
import ksw.BackEnd.RecallQuest.imagequiz.dto.ImageQuizRequestDto;
import ksw.BackEnd.RecallQuest.entity.Login;
import ksw.BackEnd.RecallQuest.member.dao.LoginDao;
import ksw.BackEnd.RecallQuest.member.dao.MemberDao;
import ksw.BackEnd.RecallQuest.entity.*;
import ksw.BackEnd.RecallQuest.imagequizdistractor.repository.ImageQuizDistractorRepository;
import ksw.BackEnd.RecallQuest.imagequiz.repository.ImageQuizRepository;
import ksw.BackEnd.RecallQuest.imagequiz.repository.QuestionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageQuizService {

    private final QuestionImageRepository questionImageRepository; //질문이미지
    private final ImageQuizRepository imageQuizRepository; //이미지퀴즈
    private final ImageQuizDistractorRepository imageQuizDistractorRepository;
    private final MemberDao memberDao;
    private final LoginDao loginDao;
    private final String FOLDER_PATH="/Users/gim-yena/Desktop/imageFile/";

    /**
     * 문제와 이미지 함께 저장
     */
    public ImageQuiz imageQuizSave(ImageQuizRequestDto imageQuizRequestDto, List<MultipartFile> files) throws IOException {

        String userLoginId = imageQuizRequestDto.getUserLoginId();
        Login login = loginDao.findByUserLoginId(imageQuizRequestDto.getUserLoginId());
        Member member = memberDao.findMemberSeq(login.getMember().getMemberSeq());


        ImageQuiz imageQuiz = ImageQuiz.builder()
                .question(imageQuizRequestDto.getQuestion())
                .hint(imageQuizRequestDto.getHint())
                .member(member)
                .build();

        imageQuizRepository.save(imageQuiz);

        for (MultipartFile file : files) {

            String filePath = FOLDER_PATH + file.getOriginalFilename();
            String storeFilename = changedImageName(file.getOriginalFilename());

            QuestionImage questionImage = QuestionImage.builder()
                    .originFilename(file.getOriginalFilename())
                    .storeFilename(storeFilename)
                    .type(file.getContentType())
                    .filePath(filePath)
                    .imageQuiz(imageQuiz)
                    .build();

            // 이미지 퀴즈에 이미지 정보 추가
            imageQuiz.getQuestionImages().add(questionImage);

            questionImageRepository.save(questionImage);

            file.transferTo(new File(filePath));

        }

        return imageQuiz;

    }

    /*
     * 이미지 퀴즈 조회
     */
    public ImageQuiz findImageQuiz(String imageQuizName) {
        ImageQuiz imageQuiz = imageQuizRepository.findByQuestion(imageQuizName);
        return imageQuiz;
    }

    public ImageQuiz findImageQuiz(Long imageQuizSeq) {
        Optional<ImageQuiz> imageQuiz = imageQuizRepository.findById(imageQuizSeq);
        return imageQuiz.orElseThrow(() -> new DataNotFoundException("존재하지 않는 문제 번호 입니다."));
    }

    public List<ImageQuiz> findImageQuizzes() {
        List<ImageQuiz> imageQuizs = imageQuizRepository.findAll();
        return imageQuizs;
    }

    public List<ImageQuizDistractor> getImageQuizDistractorsByImageQuizId(Long imageQuizId) {
        return imageQuizDistractorRepository.findByImageQuizId(imageQuizId);
    }

    public ImageQuiz findQuiz(Long imageQuizSeq) {
        Optional<ImageQuiz> imageQuiz = imageQuizRepository.findByIdWithDistractorsAndImages(imageQuizSeq);
        return imageQuiz.orElseThrow(() -> new DataNotFoundException("존재하지 않는 문제 번호 입니다."));
    }



    /*
     * 이미지 퀴즈 수정, 퀴즈 질문으로 조회해 옴
     */
    @Transactional
    public ImageQuiz updateImageQuiz(ImageQuizRequestDto imageQuizRequestDto, List<MultipartFile> files) throws IOException {

        ImageQuiz imageQuiz = imageQuizRepository.findByQuestion(imageQuizRequestDto.getQuestion());
        imageQuiz.changeInfo(imageQuizRequestDto);

        // 이미지 퀴즈를 저장
        imageQuizRepository.save(imageQuiz);

        // 기존에 연결된 이미지 정보를 모두 삭제
        imageQuiz.getQuestionImages().clear();
        questionImageRepository.deleteByImageQuiz(imageQuiz);

        // 새로운 이미지 정보를 추가
        for (MultipartFile file : files) {
            String filePath = FOLDER_PATH + file.getOriginalFilename();
            QuestionImage questionImage = QuestionImage.builder()
                    .originFilename(file.getOriginalFilename())
                    .storeFilename(changedImageName(file.getOriginalFilename()))
                    .type(file.getContentType())
                    .filePath(filePath)
                    .imageQuiz(imageQuiz)
                    .build();

            imageQuiz.getQuestionImages().add(questionImage);
            questionImageRepository.save(questionImage);

            file.transferTo(new File(filePath));
        }

        return imageQuiz;
    }

    /*
    이미지 퀴즈 삭제
     */
    public ImageQuiz deleteImageQuiz(Long imageQuizSeq) {
        ImageQuiz imageQuiz = imageQuizRepository.findById(imageQuizSeq).orElseThrow(() -> new DataNotFoundException("존재하지 않는 문제 번호 입니다."));
        imageQuizRepository.deleteById(imageQuizSeq);
        return imageQuiz;
    }

    //이미지 불러오기
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<QuestionImage> questionImage = questionImageRepository.findByOriginFilename(fileName);
        String filePath=questionImage.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    private static String changedImageName(String originName) {
        String random = UUID.randomUUID().toString();
        return random+originName;
    }
}
