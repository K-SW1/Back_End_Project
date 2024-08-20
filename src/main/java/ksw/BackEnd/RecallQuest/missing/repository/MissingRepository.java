package ksw.BackEnd.RecallQuest.missing.repository;

import ksw.BackEnd.RecallQuest.entity.Missing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

public interface MissingRepository extends JpaRepository<Missing, Integer> {


    // 전체 실종자 목록 조회 (날짜 기준으로 내림차순 정렬)
    List<Missing> findAllByOrderByDateDesc();

    // 특정 이름을 기반으로 실종자 조회
    Optional<Missing> findByName(String name);

    // 지역별 실종자 목록을 날짜 기준으로 내림차순 정렬하여 조회
    List<Missing> findByAreaOrderByDateDesc(String area);

}
