package ksw.BackEnd.RecallQuest.imagequiz.service;

import ksw.BackEnd.RecallQuest.DataNotFoundException;
import ksw.BackEnd.RecallQuest.common.Exception.imagequiz.ImageQuizAlreadyExistsException;
import ksw.BackEnd.RecallQuest.common.Exception.member.MailAlreadyExistsException;
import ksw.BackEnd.RecallQuest.imagequiz.dao.ImageQuizDao;
import ksw.BackEnd.RecallQuest.imagequiz.dao.QuestionImageDao;
import ksw.BackEnd.RecallQuest.imagequiz.dto.ImageQuizReadDto;
import ksw.BackEnd.RecallQuest.imagequiz.dto.ImageQuizRequestDto;
import ksw.BackEnd.RecallQuest.entity.Login;
import ksw.BackEnd.RecallQuest.imagequiz.dto.UpdateRequestDto;
import ksw.BackEnd.RecallQuest.member.dao.LoginDao;
import ksw.BackEnd.RecallQuest.member.dao.MemberDao;
import ksw.BackEnd.RecallQuest.entity.*;
import ksw.BackEnd.RecallQuest.imagequizdistractor.repository.ImageQuizDistractorRepository;
import ksw.BackEnd.RecallQuest.imagequiz.repository.ImageQuizRepository;
import ksw.BackEnd.RecallQuest.imagequiz.repository.QuestionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageQuizService {

    private final ImageQuizDao imageQuizDao;
    private final QuestionImageDao questionImageDao;
    private final MemberDao memberDao;
    private final LoginDao loginDao;

    private final String FOLDER_PATH="/Users/gim-yena/Desktop/imageFile/";


    /**
     * 문제와 이미지 함께 저장
     */
    public ImageQuiz imageQuizSave(ImageQuizRequestDto imageQuizRequestDto, List<MultipartFile> files) throws IOException {

        Login login = loginDao.findByUserLoginId(imageQuizRequestDto.getUserLoginId());
        Member member = memberDao.findByMemberSeq(login.getMember().getMemberSeq());

        ImageQuiz imageQuiz = ImageQuiz.builder()
                .question(imageQuizRequestDto.getQuestion())
                .hint(imageQuizRequestDto.getHint())
                .member(member)
                .build();

        existsByQuestion(imageQuiz.getQuestion());
        imageQuizDao.save(imageQuiz);

        if (files != null && !files.isEmpty()){
            for (MultipartFile file : files) {

                String originalFilename = file.getOriginalFilename();
                String storeFilename = createStoreFilename(originalFilename);
                String filePath = createPath(storeFilename);

                QuestionImage questionImage = QuestionImage.builder()
                        .originFilename(originalFilename)
                        .storeFilename(storeFilename)
                        .type(file.getContentType())
                        .filePath(filePath)
                        .imageQuiz(imageQuiz)
                        .build();

                imageQuiz.addImage(questionImage);

                questionImageDao.save(questionImage);

                file.transferTo(new File(filePath));

            }
        }

        return imageQuiz;

    }

    /*
    퀴즈 내용 중복 검사
     */
    private void existsByQuestion(String question) {
        Boolean result = imageQuizDao.existsByQuestion(question);
        if (result) {
            throw new ImageQuizAlreadyExistsException("이미 존재하는 퀴즈내용 입니다.");
        }
    }

    /*
     * 이미지 퀴즈 수정, 퀴즈 내용으로 조회해 옴
     */
    @Transactional
    public ImageQuiz updateImageQuiz(UpdateRequestDto updateRequestDto, List<MultipartFile> files) throws IOException {

        //아이디로 조회
//        ImageQuiz imageQuiz = imageQuizRepository.findById(updateRequestDto.getImageQuizId()).orElseThrow(() -> new DataNotFoundException("회원을 찾을 수 없습니다."));

        //내용으로 조회
        ImageQuiz imageQuiz = findImageQuiz(updateRequestDto);

        existsByQuestion(updateRequestDto.getRevisedQuestion());
        imageQuiz.changeInfo(updateRequestDto);

        // 이미지 퀴즈를 저장
        imageQuizDao.save(imageQuiz);

        if (files != null && !files.isEmpty()) {

            //로컬에 저장한 이미지 삭제
            List<QuestionImage> questionImageList = imageQuiz.getQuestionImages();
            for (QuestionImage questionImage : questionImageList) {
                Files.deleteIfExists(Paths.get(questionImage.getFilePath()));
            }

            // 기존에 연결된 이미지 정보를 모두 삭제
            imageQuiz.getQuestionImages().clear();
            questionImageDao.deleteByImageQuiz(imageQuiz);


            // 새로운 이미지 정보를 추가
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String storeFilename = createStoreFilename(originalFilename);
                String filePath = createPath(storeFilename);

                QuestionImage questionImage = QuestionImage.builder()
                        .originFilename(originalFilename)
                        .storeFilename(storeFilename)
                        .type(file.getContentType())
                        .filePath(filePath)
                        .imageQuiz(imageQuiz)
                        .build();

                imageQuiz.getQuestionImages().add(questionImage);
                questionImageDao.save(questionImage);

                file.transferTo(new File(filePath));
            }
        }

        return imageQuiz;
    }


    /*
     * 이미지 퀴즈 조회
     */
    public ImageQuiz findImageQuiz(ImageQuizReadDto imageQuizReadDto) {
        ImageQuiz imageQuiz = imageQuizDao.findByQuestion(imageQuizReadDto.getQuestion());
        return imageQuiz;
    }

    public ImageQuiz findImageQuiz(UpdateRequestDto updateRequestDto) {
        ImageQuiz imageQuiz = imageQuizDao.findByQuestion(updateRequestDto.getPastQuestion());
        return imageQuiz;
    }

    public ImageQuiz findImageQuiz(Long imageQuizSeq) {
        ImageQuiz imageQuiz = imageQuizDao.findById(imageQuizSeq);
        return imageQuiz;
    }

    public List<ImageQuiz> findImageQuizzes() {
        List<ImageQuiz> imageQuizzes = imageQuizDao.findAll();
        return imageQuizzes;
    }

    public List<ImageQuiz> findImageQuizzes(Long memberSeq) {
        List<ImageQuiz> imageQuizzes = imageQuizDao.findByMemberSeq(memberSeq);
        return imageQuizzes;
    }

    public List<ImageQuiz> findImageQuizzes(String userLoginId) {
        List<ImageQuiz> imageQuizzes = imageQuizDao.findByUserLoginId(userLoginId);
        return imageQuizzes;
    }


    /*
    이미지 퀴즈 삭제
     */
    public ImageQuiz deleteImageQuiz(Long imageQuizSeq) throws IOException {

        ImageQuiz imageQuiz = imageQuizDao.findById(imageQuizSeq);
        imageQuizDao.delete(imageQuizSeq);


        //로컬에 저장한 이미지 삭제
        List<QuestionImage> questionImageList = imageQuiz.getQuestionImages();
        for (QuestionImage questionImage : questionImageList){
            Files.deleteIfExists(Paths.get(questionImage.getFilePath()));
        }
        return imageQuiz;
    }

    public ImageQuiz deleteImageQuizByQuestion(ImageQuizReadDto imageQuizReadDto) throws IOException {

        ImageQuiz imageQuiz = imageQuizDao.findByQuestion(imageQuizReadDto.getQuestion());
        imageQuizDao.delete(imageQuiz);


        //로컬에 저장한 이미지 삭제
        List<QuestionImage> questionImageList = imageQuiz.getQuestionImages();
        for (QuestionImage questionImage : questionImageList){
            Files.deleteIfExists(Paths.get(questionImage.getFilePath()));
        }
        return imageQuiz;
    }

    /*
     *이미지 생성과 관련된 함수
     */
    public String createPath(String storeFilename) {
        return FOLDER_PATH+storeFilename;
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        String storeFilename = uuid + ext;

        return storeFilename;
    }

    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);
        return ext;
    }

    //바이트 코드로 이미지 불러오기
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        QuestionImage questionImage = questionImageDao.findByStoreFilename(fileName);
        String filePath = questionImage.getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

}
