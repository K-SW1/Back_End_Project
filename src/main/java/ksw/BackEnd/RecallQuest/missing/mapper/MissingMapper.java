package ksw.BackEnd.RecallQuest.missing.mapper;


import ksw.BackEnd.RecallQuest.entity.Missing;
import ksw.BackEnd.RecallQuest.missing.dto.MissingResponseDto;
import ksw.BackEnd.RecallQuest.missing.dto.MissingRequestDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class MissingMapper {

    // 엔티티를 응답 DTO로 변환
    public MissingResponseDto toResponseDto(Missing missing) {
        return MissingResponseDto.builder()
                .name(missing.getName())
                .age(missing.getAge())
                .date(missing.getDate())
                .area(missing.getArea())
                .gender(missing.getGender())
                .img(missing.getImg())
                .build();
    }

    // 요청 DTO를 엔티티로 변환
    public Missing toEntity(MissingRequestDto requestDto) {
        return Missing.builder()
                .name(requestDto.getName())
                .age(requestDto.getAge())
                .date(requestDto.getDate())
                .area(requestDto.getArea())
                .gender(requestDto.getGender())
                .img(requestDto.getImg())
                .build();
    }

    // 엔티티 목록을 응답 DTO 목록으로 변환
    public List<MissingResponseDto> toResponseDtoList(List<Missing> missingList) {
        return missingList.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }
}