package ksw.BackEnd.RecallQuest.diary.repository;

import ksw.BackEnd.RecallQuest.entity.Diary;
import ksw.BackEnd.RecallQuest.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {

    Optional<Diary> findById(Integer diaryId);

    List<Diary> findAllByMember(Member member);
}
