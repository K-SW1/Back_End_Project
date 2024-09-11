package ksw.BackEnd.RecallQuest.diary.controller;

import ksw.BackEnd.RecallQuest.diary.dto.DiaryRequestDto;
import ksw.BackEnd.RecallQuest.diary.dto.DiaryResponseDto;
import ksw.BackEnd.RecallQuest.diary.mapper.DiaryMapper;
import ksw.BackEnd.RecallQuest.diary.service.DiaryService;
import ksw.BackEnd.RecallQuest.entity.Diary;
import ksw.BackEnd.RecallQuest.jwt.dto.CustomUserDetails;
import ksw.BackEnd.RecallQuest.common.KsResponse;
import ksw.BackEnd.RecallQuest.common.code.SuccessCode;
import ksw.BackEnd.RecallQuest.common.model.ResBodyModel;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;
    private final DiaryMapper diaryMapper;

    /**
     * 일기 저장
     */
    @PostMapping("/add")
    public ResponseEntity<ResBodyModel> addDiary(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody DiaryRequestDto requestDto
    ) throws IOException {
        requestDto.setUserLoginId(customUserDetails.getUsername());
        Diary savedDiary = diaryService.addDiary(requestDto);
        DiaryResponseDto responseDto = diaryMapper.toResponseDto(savedDiary);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }

    /**
     * 모든 일기 조회 - 멤버 seq 확인 해서 조회
     */

    @GetMapping("/by-date")  //http://localhost:8088/diary/by-date?date=2024-09-11
    public ResponseEntity<ResBodyModel> getDiariesByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Diary> diaries = diaryService.getDiariesByDate(date);
        List<DiaryResponseDto> responseDtos = diaryMapper.toResponseDtoList(diaries);
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }

    /**
     * 일기 삭제
     */
    @DeleteMapping("/{diaryId}/delete")
    public ResponseEntity<ResBodyModel> deleteDiary(@PathVariable int diaryId) throws IOException, IllegalAccessException {
        diaryService.deleteDiary(diaryId);
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }
}
