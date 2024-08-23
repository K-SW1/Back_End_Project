package ksw.BackEnd.RecallQuest.missing.service;

import ksw.BackEnd.RecallQuest.missing.dto.MissingRequestDto;
import ksw.BackEnd.RecallQuest.missing.dto.MissingResponseDto;
import ksw.BackEnd.RecallQuest.missing.dao.JpaMissingDao;
import ksw.BackEnd.RecallQuest.entity.Missing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissingService {

    private final JpaMissingDao jpaMissingDao;

    // 전체 실종자 목록 조회 (날짜 기준으로 내림차순 정렬)
    public List<Missing> getAllSortedByDate() {
        List<Missing> missingList = jpaMissingDao.findAllSortedByDate();
        return missingList;
    }

//    // 특정 이름을 기반으로 실종자 조회
//    public Missing getByName(String name) {
//        Missing missing = jpaMissingDao.findByName(name);
//        return missing;
//    }
//
//    // 지역별 실종자 목록을 날짜 기준으로 내림차순 정렬하여 조회
//    public List<Missing> getByAreaSortedByDate(String area) {
//        List<Missing> missingList = jpaMissingDao.findByAreaSortedByDate(area);
//        return missingList;
//    }

}
