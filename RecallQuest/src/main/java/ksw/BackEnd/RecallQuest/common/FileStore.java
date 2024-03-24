//package ksw.BackEnd.RecallQuest.common;
//
//import ksw.BackEnd.RecallQuest.domain.QuestionImage;
//import ksw.BackEnd.RecallQuest.repository.QuestionImageRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class FileStore {
//
//    @Value("${file.dir}/")
//    private String fileDirPath;
//    private final QuestionImageRepository questionImageRepository;
//
//    public List<QuestionImage> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
//        List<QuestionImage> questionImages = new ArrayList<>();
//        for (MultipartFile multipartFile : multipartFiles) {
//            if (!multipartFile.isEmpty()) {
//                questionImages.add(storeFile(multipartFile));
//            }
//        }
//
//        return questionImages;
//    }
//
//    public QuestionImage storeFile(MultipartFile multipartFile) throws IOException {
//        if (multipartFile.isEmpty()) {
//            return null;
//        }
//
//        String originalFilename = multipartFile.getOriginalFilename();
//        String storeFilename = createStoreFilename(originalFilename);
//        multipartFile.transferTo(new File(createPath(storeFilename)));
//
//        QuestionImage questionImage =  QuestionImage.builder()
//                        .originFilename(originalFilename)
//                        .storeFilename(storeFilename)
//                        .build();
//
//        questionImageRepository.save(questionImage);
//        return questionImage;
//    }
//
//    public String createPath(String storeFilename) {
//        String viaPath = "images/";
//        return fileDirPath+viaPath+storeFilename;
//    }
//
//    private String createStoreFilename(String originalFilename) { //저장된 이름을 고유한 아이디를 가진 이름으로 변경
//        String uuid = UUID.randomUUID().toString();
//        String ext = extractExt(originalFilename);
//        String storeFilename = uuid + ext; //uuid뒤에 확장자 붙이기
//
//        return storeFilename;
//    }
//
//    private String extractExt(String originalFilename) { //확장자 뽑아내는 메서드 .jpg
//        int idx = originalFilename.lastIndexOf(".");
//        String ext = originalFilename.substring(idx);
//        return ext;
//    }
//
//}
