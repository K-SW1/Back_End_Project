package ksw.BackEnd.RecallQuest.ImagequizDistractor.service;

import ksw.BackEnd.RecallQuest.Imagequiz.dao.JpaImageQuizDao;
import ksw.BackEnd.RecallQuest.ImagequizDistractor.dao.JpaImageDistractorDao;
import ksw.BackEnd.RecallQuest.ImagequizDistractor.dto.ImageDistractorRequestDto;
import ksw.BackEnd.RecallQuest.entity.ImagequizDistractor;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.Login;
import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.member.dao.LoginDao;
import ksw.BackEnd.RecallQuest.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageDistractorService {

    private final JpaImageQuizDao jpaImageQuizDao;
    private final JpaImageDistractorDao jpaImageDistractorDao;
    private final MemberDao memberDao;
    private final LoginDao loginDao;

    /**
     * 추가 서비스
     */
    // 이미지 선택지 단일 추가
    public ImagequizDistractor addImageDistractorToQuiz(int imageQuizId, ImageDistractorRequestDto distractorRequestDto) throws IOException {
        ImageQuiz imageQuiz = jpaImageQuizDao.findById(imageQuizId);

        ImagequizDistractor distractor = ImagequizDistractor.builder()
                .imageDistractor(distractorRequestDto.getImageQuizDistractor())
                .validation(distractorRequestDto.isValidation())
                .imageQuiz(imageQuiz)
                .build();

        try {
            return jpaImageDistractorDao.save(distractor);
        } catch (DataAccessException e) {
            throw new IOException("이미지 퀴즈 선택지 추가에 실패하였습니다.", e);
        }
    }

    // 이미지 선택지 리스트 추가
    public List<ImagequizDistractor> addImageDistractorsToQuiz(int imageQuizId, List<ImageDistractorRequestDto> requestDtos) throws IOException {
        ImageQuiz imageQuiz = jpaImageQuizDao.findById(imageQuizId);

        List<ImagequizDistractor> distractors = requestDtos.stream()
                .map(requestDto -> ImagequizDistractor.builder()
                        .imageDistractor(requestDto.getImageQuizDistractor())
                        .validation(requestDto.isValidation())
                        .imageQuiz(imageQuiz)
                        .build())
                .collect(Collectors.toList());

        try {
            return jpaImageDistractorDao.saveAll(distractors);
        } catch (DataAccessException e) {
            throw new IOException("이미지 퀴즈 선택지 리스트 추가에 실패하였습니다.", e);
        }
    }

    /**
     * 조회 서비스
     */
    // 이미지 선택지 단일 조회
    public List<ImagequizDistractor> getImageDistractorsByQuizId(int imageQuizId) {
        return jpaImageDistractorDao.findByImageQuiz_ImageQuizId(imageQuizId);
    }

    // 문제 단일 조회 (문제와 선택지 조회)
    public ImageQuiz getImageQuizWithDistractors(int imageQuizId) throws IllegalAccessException {
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

    // 문제 전체 조회
    public List<ImageQuiz> getAllImageQuizzes() {
        String username = getUsernameFromSecurityContext();
        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();

        return jpaImageQuizDao.findAllByMember_MemberSeq(loggedInMember.getMemberSeq());
    }

    // SecurityContext에서 사용자 이름을 가져오는 메서드
    private String getUsernameFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    /**
     * 수정 서비스
     */
    // 이미지 선택지 수정
    @Transactional
    public List<ImagequizDistractor> updateImageDistractors(int imageQuizId, List<ImageDistractorRequestDto> updatedDistractorsRequestDto) {
        ImageQuiz imageQuiz = jpaImageQuizDao.findById(imageQuizId);

        // 기존 선택지를 삭제
        List<ImagequizDistractor> existingDistractors = jpaImageDistractorDao.findByImageQuiz_ImageQuizId(imageQuizId);
        jpaImageDistractorDao.deleteAll(existingDistractors);

        // 새로운 선택지 추가
        List<ImagequizDistractor> updatedDistractors = updatedDistractorsRequestDto.stream()
                .map(dto -> ImagequizDistractor.builder()
                        .imageDistractor(dto.getImageQuizDistractor())
                        .validation(dto.isValidation())
                        .imageQuiz(imageQuiz)
                        .build())
                .collect(Collectors.toList());

        return jpaImageDistractorDao.saveAll(updatedDistractors);
    }

    /**
     * 삭제 서비스
     */
    // 이미지 선택지 단일 삭제
    @Transactional
    public void deleteImageDistractor(int imageDistractorId) {
        ImagequizDistractor imageDistractor = jpaImageDistractorDao.findById(imageDistractorId);
        jpaImageDistractorDao.deleteByImageDistractorId(imageDistractor);
    }

    // 이미지 선택지 전체 삭제
    @Transactional
    public void deleteAllDistractorsByImageQuizId(int imageQuizId) {
        List<ImagequizDistractor> distractors = jpaImageDistractorDao.findByImageQuiz_ImageQuizId(imageQuizId);
        jpaImageDistractorDao.deleteAll(distractors);
    }
}
