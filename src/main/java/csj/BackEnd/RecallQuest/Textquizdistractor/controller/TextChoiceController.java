package csj.BackEnd.RecallQuest.Textquizdistractor.controller;



import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizWithChoicesResponseDto;
import csj.BackEnd.RecallQuest.Textquizdistractor.dto.TextChoiceRequestDto;
import csj.BackEnd.RecallQuest.Textquizdistractor.dto.TextChoiceResponseDto;
import csj.BackEnd.RecallQuest.Textquizdistractor.service.TextChoiceService;
import csj.BackEnd.RecallQuest.common.AetResponse;
import csj.BackEnd.RecallQuest.common.code.SuccessCode;
import csj.BackEnd.RecallQuest.common.model.ResBodyModel;
import csj.BackEnd.RecallQuest.entity.TextChoice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/quizs/textquiz")
public class TextChoiceController {



    private final TextChoiceService textChoiceService;




    // [TextQuiz](선택지)(정답) 추가
    @PostMapping("/{textQuizId}/choices/add")
    public ResponseEntity<ResBodyModel> addTextChoicesToQuiz(@PathVariable("textQuizId") int textQuizId,
                                                             @RequestBody List<TextChoice> choices) {
        List<TextChoiceRequestDto> requestDtos = choices.stream()
                .map(this::convertToRequestDto)
                .collect(Collectors.toList());

        List<TextChoice> savedChoices = textChoiceService.addTextChoicesToQuiz(textQuizId, requestDtos);

        List<TextChoiceResponseDto> responseDtos = savedChoices.stream()
                .map(choice -> {
                    TextChoiceResponseDto responseDto = new TextChoiceResponseDto();
                    responseDto.setChoiceText(choice.getChoiceText());
                    responseDto.setAnswer(choice.isAnswer());
                    return responseDto;
                })
                .collect(Collectors.toList());

        // AetResponse를 사용하여 ResponseEntity를 생성
        return AetResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }
    private TextChoiceRequestDto convertToRequestDto(TextChoice choice) {
        TextChoiceRequestDto requestDto = new TextChoiceRequestDto();
        requestDto.setChoiceText(choice.getChoiceText());
        requestDto.setAnswer(choice.isAnswer());
        return requestDto;
    }








    // [TextQuiz](선택지)(정답) 조회
    @GetMapping("/{textQuizId}/choices")
    public ResponseEntity<ResBodyModel> getTextChoicesByQuizId(@PathVariable("textQuizId") int textQuizId) {
        List<TextChoiceResponseDto> textChoices = textChoiceService.getTextChoicesByQuizId(textQuizId);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return AetResponse.toResponse(SuccessCode.SUCCESS, textChoices);
    }


    // [TextQuiz](힌트)(선택지)(정답) 특정 조회
    @GetMapping("/{textQuizId}/details")
    public ResponseEntity<ResBodyModel> getTextQuizWithChoices(@PathVariable int textQuizId) {
        // 텍스트 퀴즈와 해당하는 선택지를 가져오는 서비스 메서드를 호출합니다.
        TextQuizWithChoicesResponseDto responseDto = textChoiceService.getTextQuizWithChoices(textQuizId);

        // AetResponse를 사용하여 응답을 반환합니다.
        return AetResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }


    // [TextQuiz](힌트)(선택지)(정답) 전체 조회
    @GetMapping("/every")
    public ResponseEntity<ResBodyModel> getAllTextQuizzesWithChoices() {
        // 모든 텍스트 퀴즈와 해당하는 선택지를 가져오는 서비스 메서드를 호출합니다.
        List<TextQuizWithChoicesResponseDto> responseDtos = textChoiceService.getAllTextQuizzesWithChoices();

        // AetResponse를 사용하여 응답을 반환합니다.
        return AetResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }






    // [TextQuiz](선택지) 수정 컨트롤러
    @PutMapping("/{textQuizId}/choices/update")
    public ResponseEntity<ResBodyModel> updateTextChoices(@PathVariable int textQuizId,
                                                          @RequestBody List<TextChoiceRequestDto> updatedChoicesRequestDto) {
        // 서비스 메서드 호출
        List<TextChoiceResponseDto> updatedChoicesResponseDto = textChoiceService.updateTextChoices(textQuizId, updatedChoicesRequestDto);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return AetResponse.toResponse(SuccessCode.SUCCESS, updatedChoicesResponseDto);
    }








}

//    {
//        "question": "좋아하는 계절은?",
//            "hint": "눈이 내린다."
//    }


//[
//  {
//   "choiceText": "봄",
//   "answer": false
//   },
//   {
//   "choiceText": "여름",
//   "answer": false
//    },
//    {
//    "choiceText": "가을",
//     "answer": false
//    },
//    {
//      "choiceText": "겨울",
//      "answer": true
//    }
//]

//spring.jpa.hibernate.ddl-auto=create DB생성 코드