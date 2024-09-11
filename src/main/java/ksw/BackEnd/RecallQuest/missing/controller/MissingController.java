package ksw.BackEnd.RecallQuest.missing.controller;

import ksw.BackEnd.RecallQuest.common.code.SuccessCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;
import ksw.BackEnd.RecallQuest.common.KsResponse;
import ksw.BackEnd.RecallQuest.entity.Missing;
import ksw.BackEnd.RecallQuest.missing.dto.MissingRequestDto;
import ksw.BackEnd.RecallQuest.missing.dto.MissingResponseDto;
import ksw.BackEnd.RecallQuest.missing.mapper.MissingMapper;
import ksw.BackEnd.RecallQuest.missing.service.MissingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missing")
public class MissingController {

    private final MissingService missingService;
    private final MissingMapper missingMapper;

    /**
     * 조회
     */

    // 전체 실종자 목록 조회 (날짜 기준으로 내림차순 정렬)
    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> getAllSortedByDate() {
        List<MissingResponseDto> responseDtos = missingMapper.toResponseDtoList(missingService.getAllSortedByDate());
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }


    /**
     * 1회 이후 다른 이름 검색 시 오류 해결 찾기 -> 콘솔에서 하이버네이트 출력이 되지 않음.
     */

    // 특정 이름을 기반으로 실종자 조회
//    @GetMapping("/name/{name}")
//    public ResponseEntity<ResBodyModel> getByName(@PathVariable String name) {
//        MissingResponseDto responseDto = missingMapper.toResponseDto(missingService.getByName(name));
//        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
//    }



    // 지역별 실종자 목록을 날짜 기준으로 내림차순 정렬하여 조회
//    @GetMapping("/area/{area}")
//    public ResponseEntity<ResBodyModel> getByAreaSortedByDate(@PathVariable String area) {
//        List<MissingResponseDto> responseDtos = missingMapper.toResponseDtoList(missingService.getByAreaSortedByDate(area));
//        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
//    }


}
