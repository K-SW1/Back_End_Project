package ksw.BackEnd.RecallQuest.Imagequiz.service;

import ksw.BackEnd.RecallQuest.Imagequiz.dao.JpaImageQuizDao;
import ksw.BackEnd.RecallQuest.Imagequiz.dto.ImageQuizRequestDto;
import ksw.BackEnd.RecallQuest.entity.Login;
import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.member.dao.LoginDao;
import ksw.BackEnd.RecallQuest.member.dao.MemberDao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageQuizService {

    private final JpaImageQuizDao jpaImageQuizDao;
    private final MemberDao memberDao;
    private final LoginDao loginDao;

    // 이미지 저장 경로 설정 (로컬 경로 또는 클라우드 스토리지 경로)
    private final String imageUploadDir = "C:/Users/user/Desktop/";

    /**
     * 이미지 퀴즈 저장
     */

    public String saveImage(MultipartFile imageFile) throws IOException {
        // 파일이 비어있다면 예외 처리
        if (imageFile.isEmpty()) {
            throw new IOException("빈 파일입니다.");
        }

        // 저장할 파일 이름 생성 (UUID + 원본 파일 이름)
        String originalFilename = imageFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String savedFileName = UUID.randomUUID().toString() + extension;

        // 파일 저장 경로 설정
        Path imagePath = Paths.get(imageUploadDir, savedFileName);

        // 디렉토리가 없으면 생성
        if (!Files.exists(imagePath.getParent())) {
            Files.createDirectories(imagePath.getParent());
        }

        // 파일 저장
        Files.copy(imageFile.getInputStream(), imagePath);

        // 저장된 이미지의 URL 리턴 (필요 시 URL 경로를 적절히 설정)
        return "/images/" + savedFileName;
    }

    // 이미지 퀴즈 저장 로직
    public ImageQuiz addImageQuiz(ImageQuizRequestDto requestDto) throws IOException {
        // 사용자 ID로 로그인 정보 조회
        Login login = loginDao.findByUserLoginId(requestDto.getUserLoginId());

        // 로그인 정보에서 회원 정보 조회
        Member member = memberDao.findByMemberSeq(login.getMember().getMemberSeq());

        // ImageQuiz 객체 생성
        ImageQuiz imageQuiz = ImageQuiz.builder()
                .member(member)
                .question(requestDto.getQuestion())
                .hint(requestDto.getHint())
                .imageUrl(requestDto.getImageUrl())  // 이미지 URL 추가
                .build();

        // 질문 중복 여부 확인
        existsByQuestion(imageQuiz.getQuestion());

        try {
            // 이미지 퀴즈 데이터 저장
            jpaImageQuizDao.save(imageQuiz);
        } catch (DataAccessException e) {
            throw new IOException("이미지퀴즈 저장 실패", e);
        }

        return imageQuiz;
    }



    /**
     * 퀴즈 내용 중복 검사
     */
    private void existsByQuestion(String question) {
        Boolean result = jpaImageQuizDao.existsByQuestion(question);
        if (result) {
            throw new IllegalArgumentException("이미 존재하는 퀴즈내용 입니다.");
        }
    }


    /**
     * 이미지 퀴즈 수정
     */
    public ImageQuiz updateImageQuiz(int imageQuizId, ImageQuizRequestDto updatedImageQuizRequestDto, MultipartFile imageFile) throws IOException {
        // 기존 이미지 퀴즈 조회
        ImageQuiz existingImageQuiz = jpaImageQuizDao.findById(imageQuizId);

        // 요청 DTO의 필드를 기존 엔티티로 업데이트
        existingImageQuiz.setQuestion(updatedImageQuizRequestDto.getQuestion());
        existingImageQuiz.setHint(updatedImageQuizRequestDto.getHint());

        // 이미지 파일 업데이트
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile); // saveImage 메서드를 호출하여 이미지 저장
            existingImageQuiz.setImageUrl(imageUrl);
        }

        try {
            // 변경된 이미지 퀴즈 데이터 저장
            return jpaImageQuizDao.save(existingImageQuiz);
        } catch (DataAccessException e) {
            throw new IOException("이미지 퀴즈 업데이트 실패", e);
        }
    }



    /**
     * 이미지 퀴즈 조회
     */
    public List<ImageQuiz> getAllImageQuizzes() {
        String username = getUsernameFromSecurityContext();
        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();

        return jpaImageQuizDao.findAllByMember(loggedInMember);
    }

    // 이미지 퀴즈 단일 조회
    public ImageQuiz getImageQuizById(int imageQuizId) throws IllegalAccessException {
        String username = getUsernameFromSecurityContext();
        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();

        ImageQuiz imageQuiz = jpaImageQuizDao.findById(imageQuizId);

        // 퀴즈 접근 권한 확인
        if (!imageQuiz.getMember().getMemberSeq().equals(loggedInMember.getMemberSeq())) {
            throw new IllegalAccessException("이 퀴즈에 대한 조회 권한이 없습니다.");
        }

        return imageQuiz;
    }


    /**
     * 이미지 퀴즈 및 보기 정답 조회
     */
    public ImageQuiz getImageQuizByQuestion(String question) throws IllegalAccessException {
        String username = getUsernameFromSecurityContext();
        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();

        ImageQuiz imageQuiz = jpaImageQuizDao.findByQuestion(question);

        // 퀴즈 접근 권한 확인
        if (!imageQuiz.getMember().getMemberSeq().equals(loggedInMember.getMemberSeq())) {
            throw new IllegalAccessException("이 퀴즈에 대한 조회 권한이 없습니다.");
        }

        return imageQuiz;
    }

    /**
     * 이미지 퀴즈 삭제
     */
    @Transactional
    public void deleteImageQuiz(int imageQuizId) throws IOException, IllegalAccessException {
        String username = getUsernameFromSecurityContext();
        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();

        ImageQuiz imageQuiz = jpaImageQuizDao.findById(imageQuizId);

        if (!imageQuiz.getMember().getMemberSeq().equals(loggedInMember.getMemberSeq())) {
            throw new IllegalAccessException("이 퀴즈에 대한 삭제 권한이 없습니다.");
        }

        try {
            jpaImageQuizDao.delete(imageQuiz);
        } catch (DataAccessException e) {
            throw new IOException("이미지퀴즈 삭제를 실패하였습니다.", e);
        }
    }

    private String getUsernameFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
