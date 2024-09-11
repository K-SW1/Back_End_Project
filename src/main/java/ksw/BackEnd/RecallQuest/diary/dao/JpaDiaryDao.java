package ksw.BackEnd.RecallQuest.diary.dao;

import ksw.BackEnd.RecallQuest.diary.repository.DiaryRepository;
import ksw.BackEnd.RecallQuest.entity.Diary;
import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.common.Exception.diary.diaryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaDiaryDao {

    private final DiaryRepository diaryRepository;

    public Diary save(Diary diary) {
        return diaryRepository.save(diary);
    }

    public Diary findById(int diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new diaryNotFoundException("해당 ID의 일기를 찾을 수 없습니다"));
    }

    public List<Diary> findAll() {
        return diaryRepository.findAll();
    }

    public void delete(Diary diary) {
        diaryRepository.delete(diary);
    }

    public List<Diary> findAllByMember(Member member) {
        return diaryRepository.findAllByMember(member);
    }

    // 추가: 특정 회원의 특정 날짜에 작성된 일기 조회
    public List<Diary> findAllByMemberAndDate(Member member, LocalDate date) {
        return diaryRepository.findAllByMemberAndDate(member, date);
    }
}
