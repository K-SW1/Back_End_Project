package ksw.BackEnd.RecallQuest.diary.service;

import ksw.BackEnd.RecallQuest.diary.dao.JpaDiaryDao;
import ksw.BackEnd.RecallQuest.diary.dto.DiaryRequestDto;
import ksw.BackEnd.RecallQuest.entity.Diary;
import ksw.BackEnd.RecallQuest.entity.Login;
import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.jwt.dto.CustomUserDetails;
import ksw.BackEnd.RecallQuest.common.Exception.diary.diaryNotFoundException;
import ksw.BackEnd.RecallQuest.member.dao.LoginDao;
import ksw.BackEnd.RecallQuest.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final JpaDiaryDao jpaDiaryDao;
    private final MemberDao memberDao;
    private final LoginDao loginDao;


    /**
     * 일기 저장
     */
    public Diary addDiary(DiaryRequestDto requestDto) throws IOException {

        Login login = loginDao.findByUserLoginId(requestDto.getUserLoginId());

        Member member = memberDao.findByMemberSeq(login.getMember().getMemberSeq());

        // Diary 객체 생성
        Diary diary = Diary.builder()
                .member(member)
                .name(requestDto.getName())
                .time(requestDto.getTime())
                .memo(requestDto.getMemo())
                .date(requestDto.getDate())
                .build();

        // 일기 저장
        return jpaDiaryDao.save(diary);
    }

    /**
     * 모든 일기 조회
     */
    public List<Diary> getAllDiaries() {
        String username = getUsernameFromSecurityContext();
        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();
        return jpaDiaryDao.findAllByMember(loggedInMember);
    }

    /**
     * 일기 삭제
     */
    @Transactional
    public void deleteDiary(int diaryId) throws IOException, IllegalAccessException {
        String username = getUsernameFromSecurityContext();
        Login login = loginDao.findByUserLoginId(username);
        Member loggedInMember = login.getMember();

        Diary diary = jpaDiaryDao.findById(diaryId);

        // 권한 체크
        if (!diary.getMember().getMemberSeq().equals(loggedInMember.getMemberSeq())) {
            throw new IllegalAccessException("이 일기에 대한 삭제 권한이 없습니다.");
        }

        jpaDiaryDao.delete(diary);
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
