package ksw.BackEnd.RecallQuest.missing.dao;

import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.entity.Missing;
import ksw.BackEnd.RecallQuest.missing.repository.MissingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import ksw.BackEnd.RecallQuest.common.Exception.missing.MissingNotFoundException;

@Repository
@RequiredArgsConstructor
public class JpaMissingDao {

    private final MissingRepository missingRepository;

    // 전체 실종자 목록 조회 (날짜 기준으로 내림차순 정렬)
    public List<Missing> findAllSortedByDate() {
        return missingRepository.findAllByOrderByDateDesc();
    }

    // 특정 이름을 기반으로 실종자 조회
    public Missing findByName(String name) {
        return missingRepository.findByName(name)
                .orElseThrow(() -> new MissingNotFoundException("해당 이름의 실종자를 찾을 수 없습니다"));
    }

    // 지역별 실종자 목록을 날짜 기준으로 내림차순 정렬하여 조회
    public List<Missing> findByAreaSortedByDate(String area) {
        return missingRepository.findByAreaOrderByDateDesc(area);
    }
}
