    package ksw.BackEnd.RecallQuest.diary.mapper;

    import ksw.BackEnd.RecallQuest.entity.Diary;
    import ksw.BackEnd.RecallQuest.diary.dto.DiaryRequestDto;
    import ksw.BackEnd.RecallQuest.diary.dto.DiaryResponseDto;
    import org.springframework.stereotype.Component;

    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    import java.util.List;
    import java.util.stream.Collectors;

    @Component
    public class DiaryMapper {

        private static final DateTimeFormatter REQUEST_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
        private static final DateTimeFormatter RESPONSE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        public DiaryResponseDto toResponseDto(Diary diary) {
            return DiaryResponseDto.builder()
                    .diaryId(diary.getDiaryId())
                    .memberSeq(diary.getMember().getMemberSeq())
                    .name(diary.getName())
                    .time(diary.getTime())
                    .memo(diary.getMemo())
                    .date(diary.getDate().format(RESPONSE_FORMATTER)) // 날짜를 포맷하여 문자열로 변환
                    .build();
        }

        public List<DiaryResponseDto> toResponseDtoList(List<Diary> diaryList) {
            return diaryList.stream()
                    .map(this::toResponseDto)
                    .collect(Collectors.toList());
        }


    }
