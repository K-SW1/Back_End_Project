package csj.BackEnd.RecallQuest.Textquizdistractor.controller;



import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizWithDistractorsResponseDto;
import csj.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorRequestDto;
import csj.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorResponseDto;
import csj.BackEnd.RecallQuest.Textquizdistractor.service.TextDistractorService;
import csj.BackEnd.RecallQuest.common.KsResponse;
import csj.BackEnd.RecallQuest.common.code.SuccessCode;
import csj.BackEnd.RecallQuest.common.model.ResBodyModel;
import csj.BackEnd.RecallQuest.entity.TextDistractor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/quizs/textquiz")
public class TextDistractorController {



    private final TextDistractorService textDistractorService;




    // [TextQuiz](선택지)(정답) 추가
    @PostMapping("/{textQuizId}/distractors/add")
    public ResponseEntity<ResBodyModel> addTextDistractorsToQuiz(@PathVariable("textQuizId") int textQuizId,
                                                             @RequestBody List<TextDistractor> distractors) {
        List<TextDistractorRequestDto> requestDtos = distractors.stream()
                .map(this::convertToRequestDto)
                .collect(Collectors.toList());

        List<TextDistractor> savedDistractors = textDistractorService.addTextDistractorsToQuiz(textQuizId, requestDtos);

        List<TextDistractorResponseDto> responseDtos = savedDistractors.stream()
                .map(distractor -> {
                    TextDistractorResponseDto responseDto = new TextDistractorResponseDto();
                    responseDto.setTextzQuizDistractor(distractor.getTextzQuizDistractor());
                    responseDto.setValidation(distractor.isValidation());
                    return responseDto;
                })
                .collect(Collectors.toList());

        // AetResponse를 사용하여 ResponseEntity를 생성
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }
    private TextDistractorRequestDto convertToRequestDto(TextDistractor distractor) {
        TextDistractorRequestDto requestDto = new TextDistractorRequestDto();
        requestDto.setTextzQuizDistractor(distractor.getTextzQuizDistractor());
        requestDto.setValidation(distractor.isValidation());
        return requestDto;
    }








    // [TextQuiz](선택지)(정답) 조회
    @GetMapping("/{textQuizId}/distractors")
    public ResponseEntity<ResBodyModel> getTextDistractorsByQuizId(@PathVariable("textQuizId") int textQuizId) {
        List<TextDistractorResponseDto> textDistractors = textDistractorService.getTextDistractorsByQuizId(textQuizId);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return KsResponse.toResponse(SuccessCode.SUCCESS, textDistractors);
    }


    // [TextQuiz](힌트)(선택지)(정답) 특정 조회
    @GetMapping("/{textQuizId}/details")
    public ResponseEntity<ResBodyModel> getTextQuizWithDistractors(@PathVariable int textQuizId) {
        // 텍스트 퀴즈와 해당하는 선택지를 가져오는 서비스 메서드를 호출합니다.
        TextQuizWithDistractorsResponseDto responseDto = textDistractorService.getTextQuizWithDistractors(textQuizId);

        // AetResponse를 사용하여 응답을 반환합니다.
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }


    // [TextQuiz](힌트)(선택지)(정답) 전체 조회
    @GetMapping("/every")
    public ResponseEntity<ResBodyModel> getAllTextQuizzesWithDistractors() {
        // 모든 텍스트 퀴즈와 해당하는 선택지를 가져오는 서비스 메서드를 호출합니다.
        List<TextQuizWithDistractorsResponseDto> responseDtos = textDistractorService.getAllTextQuizzesWithDistractors();

        // AetResponse를 사용하여 응답을 반환합니다.
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }






    // [TextQuiz](선택지) 수정 컨트롤러
    @PutMapping("/{textQuizId}/distractors/update")
    public ResponseEntity<ResBodyModel> updateTextDistractors(@PathVariable int textQuizId,
                                                          @RequestBody List<TextDistractorRequestDto> updatedDistractorsRequestDto) {
        // 서비스 메서드 호출
        List<TextDistractorResponseDto> updatedDistractorsResponseDto = textDistractorService.updateTextDistractors(textQuizId, updatedDistractorsRequestDto);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return KsResponse.toResponse(SuccessCode.SUCCESS, updatedDistractorsResponseDto);
    }








}

//    {
//        "question": "좋아하는 계절은?",
//            "hint": "눈이 내린다."
//    }


//[
//  {
//   "textzQuizDistractor": "봄",
//   "validation": false
//   },
//   {
//   "textzQuizDistractor": "여름",
//   "validation": false
//    },
//    {
//    "textzQuizDistractor": "가을",
//     "validation": false
//    },
//    {
//      "textzQuizDistractor": "겨울",
//      "validation": true
//    }
//]

//spring.jpa.hibernate.ddl-auto=create