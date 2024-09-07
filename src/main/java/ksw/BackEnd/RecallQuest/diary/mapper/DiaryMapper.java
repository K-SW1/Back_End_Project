package ksw.BackEnd.RecallQuest.diary.mapper;

import ksw.BackEnd.RecallQuest.entity.Diary;
import ksw.BackEnd.RecallQuest.diary.dto.DiaryRequestDto;
import ksw.BackEnd.RecallQuest.diary.dto.DiaryResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryMapper {

    public DiaryResponseDto toResponseDto(Diary diary) {
        return DiaryResponseDto.builder()
                .diaryId(diary.getDiaryId())
                .memberSeq(diary.getMember().getMemberSeq())
                .name(diary.getName())
                .time(diary.getTime())
                .memo(diary.getMemo())
                .date(diary.getDate())
                .build();
    }

    public List<DiaryResponseDto> toResponseDtoList(List<Diary> diaryList) {
        return diaryList.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
