package ksw.BackEnd.RecallQuest.imagequiz.service;

import ksw.BackEnd.RecallQuest.DataNotFoundException;
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

        if (files != null && !files.isEmpty()){
            for (MultipartFile file : files) {

//            String filePath = FOLDER_PATH + file.getOriginalFilename();
//            String storeFilename = changedImageName(file.getOriginalFilename());

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

                // 이미지 퀴즈에 이미지 정보 추가
//            imageQuiz.getQuestionImages().add(questionImage);
                imageQuiz.addImage(questionImage);

                questionImageRepository.save(questionImage);

                file.transferTo(new File(filePath));

            }
        }

        return imageQuiz;

    }

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

    /*
     * 이미지 퀴즈 조회
     */
    public ImageQuiz findImageQuiz(ImageQuizReadDto imageQuizReadDto) {
        ImageQuiz imageQuiz = imageQuizRepository.findByQuestion(imageQuizReadDto.getQuestion()).orElseThrow(() -> new DataNotFoundException("해당 이름으로 된 퀴즈 내용을 찾을 수 없습니다."));
        return imageQuiz;
    }

    public ImageQuiz findImageQuiz(UpdateRequestDto updateRequestDto) {
        ImageQuiz imageQuiz = imageQuizRepository.findByQuestion(updateRequestDto.getPastQuestion()).orElseThrow(() -> new DataNotFoundException("해당 이름으로 된 퀴즈 내용을 찾을 수 없습니다."));
        return imageQuiz;
    }

    public ImageQuiz findImageQuiz(Long imageQuizSeq) {
        ImageQuiz imageQuiz = imageQuizRepository.findById(imageQuizSeq).orElseThrow(() -> new DataNotFoundException("회원을 찾을 수 없습니다."));
        return imageQuiz;
    }

    public List<ImageQuiz> findImageQuizzes() {
        List<ImageQuiz> imageQuizs = imageQuizRepository.findAll();
        return imageQuizs;
    }

    public List<ImageQuiz> findImageQuizzes(Long memberSeq) {
        List<ImageQuiz> imageQuizs = imageQuizRepository.findByMemberSeq(memberSeq);
        return imageQuizs;
    }

    public List<ImageQuiz> findImageQuizzes(String userLoginId) {
        List<ImageQuiz> imageQuizs = imageQuizRepository.findImageQuizzesByUserLoginId(userLoginId);
        return imageQuizs;
    }

    public List<ImageQuizDistractor> getImageQuizDistractorsByImageQuizId(Long imageQuizId) {
        return imageQuizDistractorRepository.findByImageQuizId(imageQuizId);
    }

    //이 거지같은건 뭐지?????
    public ImageQuiz findQuiz(Long imageQuizSeq) {
        ImageQuiz imageQuiz = imageQuizRepository.findByIdWithImages(imageQuizSeq)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 문제 번호 입니다."));
        List<ImageQuizDistractor> distractors = imageQuizDistractorRepository.findDistractorsByQuizId(imageQuizSeq);
        imageQuiz.setImageQuizDistractors(distractors);
        return imageQuiz;
    }





//    /*
//     * 이미지 퀴즈 수정, 퀴즈 질문으로 조회해 옴
//     */
//    @Transactional
//    public ImageQuiz updateImageQuiz(ImageQuizRequestDto imageQuizRequestDto, List<MultipartFile> files) throws IOException {
//
//        ImageQuiz imageQuiz = imageQuizRepository.findByQuestion(imageQuizRequestDto.getQuestion());
//        imageQuiz.changeInfo(imageQuizRequestDto);
//
//        // 이미지 퀴즈를 저장
//        imageQuizRepository.save(imageQuiz);
//
//        // 기존에 연결된 이미지 정보를 모두 삭제
//        imageQuiz.getQuestionImages().clear();
//        questionImageRepository.deleteByImageQuiz(imageQuiz);
//
//        // 새로운 이미지 정보를 추가
//        for (MultipartFile file : files) {
//            String filePath = FOLDER_PATH + file.getOriginalFilename();
//            QuestionImage questionImage = QuestionImage.builder()
//                    .originFilename(file.getOriginalFilename())
//                    .storeFilename(changedImageName(file.getOriginalFilename()))
//                    .type(file.getContentType())
//                    .filePath(filePath)
//                    .imageQuiz(imageQuiz)
//                    .build();
//
//            imageQuiz.getQuestionImages().add(questionImage);
//            questionImageRepository.save(questionImage);
//
//            file.transferTo(new File(filePath));
//        }
//
//        return imageQuiz;
//    }

    /*
     * 이미지 퀴즈 수정, 퀴즈 내용으로 조회해 옴
     */
    @Transactional
    public ImageQuiz updateImageQuiz(UpdateRequestDto updateRequestDto, List<MultipartFile> files) throws IOException {

        //아이디로 조회
//        ImageQuiz imageQuiz = imageQuizRepository.findById(updateRequestDto.getImageQuizId()).orElseThrow(() -> new DataNotFoundException("회원을 찾을 수 없습니다."));

        //내용으로 조회
        ImageQuiz imageQuiz = findImageQuiz(updateRequestDto);

        imageQuiz.changeInfo(updateRequestDto);

        // 이미지 퀴즈를 저장
        imageQuizRepository.save(imageQuiz);

        if (files != null && !files.isEmpty()) {

            //로컬에 저장한 이미지 삭제
            List<QuestionImage> questionImageList = imageQuiz.getQuestionImages();
            for (QuestionImage questionImage : questionImageList) {
                Files.deleteIfExists(Paths.get(questionImage.getFilePath()));
            }

            // 기존에 연결된 이미지 정보를 모두 삭제
            imageQuiz.getQuestionImages().clear();
            questionImageRepository.deleteByImageQuiz(imageQuiz);


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
                questionImageRepository.save(questionImage);

                file.transferTo(new File(filePath));
            }
        }

        return imageQuiz;
    }

    /*
    이미지 퀴즈 삭제
     */
    public ImageQuiz deleteImageQuiz(Long imageQuizSeq) throws IOException {
        ImageQuiz imageQuiz = imageQuizRepository.findById(imageQuizSeq).orElseThrow(() -> new DataNotFoundException("존재하지 않는 문제 번호 입니다."));
        imageQuizRepository.deleteById(imageQuizSeq);

        //로컬에 저장한 이미지 삭제
        List<QuestionImage> questionImageList = imageQuiz.getQuestionImages();
        for (QuestionImage questionImage : questionImageList){
            Files.deleteIfExists(Paths.get(questionImage.getFilePath()));
        }
        return imageQuiz;
    }

    public ImageQuiz deleteImageQuizByQuestion(ImageQuizReadDto imageQuizReadDto) throws IOException {
        ImageQuiz imageQuiz = imageQuizRepository.findByQuestion(imageQuizReadDto.getQuestion()).orElseThrow(() -> new DataNotFoundException("해당 이름으로 된 퀴즈 내용을 찾을 수 없습니다."));
        imageQuizRepository.delete(imageQuiz);

        //로컬에 저장한 이미지 삭제
        List<QuestionImage> questionImageList = imageQuiz.getQuestionImages();
        for (QuestionImage questionImage : questionImageList){
            Files.deleteIfExists(Paths.get(questionImage.getFilePath()));
        }
        return imageQuiz;
    }



    //이미지 불러오기
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<QuestionImage> questionImage = questionImageRepository.findByStoreFilename(fileName);
        String filePath=questionImage.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    private static String changedImageName(String originName) {
        String random = UUID.randomUUID().toString();
        return random+originName;
    }
}
