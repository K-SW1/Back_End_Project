package csj.BackEnd.RecallQuest.controller;


import csj.BackEnd.RecallQuest.domain.TextQuiz;
import csj.BackEnd.RecallQuest.domain.TextChoice;
import csj.BackEnd.RecallQuest.service.TextQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.ArrayList;
import csj.BackEnd.RecallQuest.dto.TextQuizRequestDto;
import csj.BackEnd.RecallQuest.dto.TextQuizResponseDto;
import csj.BackEnd.RecallQuest.dto.TextChoiceRequestDto;
import csj.BackEnd.RecallQuest.dto.TextChoiceResponseDto;
import java.util.stream.Collectors;
import java.awt.*;
import lombok.Data;
import org.springframework.ui.Model;
import csj.BackEnd.RecallQuest.common.AetResponse;
import csj.BackEnd.RecallQuest.common.code.SuccessCode;
import csj.BackEnd.RecallQuest.common.model.ResBodyModel;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/quizs/textquiz")
public class TextQuizController {


    @Autowired
    private final TextQuizService textQuizService;



    // [TextQuiz](힌트) 추가  + AetResponse 변경 완료
    @PostMapping("/add")
    public ResponseEntity<ResBodyModel> addTextQuiz(@RequestBody TextQuizRequestDto requestDto) {
        // 요청 DTO를 엔티티로 변환합니다
        TextQuizResponseDto responseDto = textQuizService.addTextQuiz(requestDto);

        // 적절한 상태와 함께 응답 DTO를 반환합니다
        return AetResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }

    // [TextQuiz](선택지)(정답) 추가
    @PostMapping("/{textQuizId}/choices/add")
    public ResponseEntity<ResBodyModel> addTextChoicesToQuiz(@PathVariable("textQuizId") int textQuizId,
                                                             @RequestBody List<TextChoice> choices) {
        List<TextChoiceRequestDto> requestDtos = choices.stream()
                .map(this::convertToRequestDto)
                .collect(Collectors.toList());

        List<TextChoice> savedChoices = textQuizService.addTextChoicesToQuiz(textQuizId, requestDtos);

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






    // [TextQuiz](힌트) 조회 + AetResponse 변경 완료
    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> getAllTextQuizzes() {
        List<TextQuiz> textQuizzes = textQuizService.getAllTextQuizzes();

        List<TextQuizResponseDto> responseDtos = textQuizzes.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        // AetResponse를 사용하여 ResponseEntity를 생성
        return AetResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }
    private TextQuizResponseDto convertToResponseDto(TextQuiz textQuiz) {
        TextQuizResponseDto responseDto = new TextQuizResponseDto();
        responseDto.setTextQuizId(textQuiz.getTextQuizId());
        responseDto.setMemberInfoId(textQuiz.getMemberInfoId());
        responseDto.setQuestion(textQuiz.getQuestion());
        responseDto.setHint(textQuiz.getHint());
        return responseDto;
    }


    // [TextQuiz](힌트) 특정 조회
    @GetMapping("/{textQuizId}")
    public ResponseEntity<ResBodyModel> getTextQuizById(@PathVariable int textQuizId) {
        TextQuizResponseDto textQuiz = textQuizService.getTextQuizById(textQuizId);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return AetResponse.toResponse(SuccessCode.SUCCESS, textQuiz);
    }


    // [TextQuiz](선택지)(정답) 조회
    @GetMapping("/{textQuizId}/choices")
    public ResponseEntity<ResBodyModel> getTextChoicesByQuizId(@PathVariable("textQuizId") int textQuizId) {
        List<TextChoiceResponseDto> textChoices = textQuizService.getTextChoicesByQuizId(textQuizId);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return AetResponse.toResponse(SuccessCode.SUCCESS, textChoices);
    }







    // [TextQuiz] 수정 컨트롤러 + AetResponse 변경 완료
    @PutMapping("/{textQuizId}/update")
    public ResponseEntity<ResBodyModel> updateTextQuiz(@PathVariable int textQuizId,
                                                       @RequestBody TextQuizRequestDto updatedTextQuizRequestDto) {
        TextQuizResponseDto updatedQuiz = textQuizService.updateTextQuiz(textQuizId, updatedTextQuizRequestDto);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return AetResponse.toResponse(SuccessCode.SUCCESS, updatedQuiz);
    }
    // [TextQuiz](선택지) 수정 컨트롤러
    @PutMapping("/{textQuizId}/choices/update")
    public ResponseEntity<ResBodyModel> updateTextChoices(@PathVariable int textQuizId,
                                                          @RequestBody List<TextChoiceRequestDto> updatedChoicesRequestDto) {
        // 서비스 메서드 호출
        List<TextChoiceResponseDto> updatedChoicesResponseDto = textQuizService.updateTextChoices(textQuizId, updatedChoicesRequestDto);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return AetResponse.toResponse(SuccessCode.SUCCESS, updatedChoicesResponseDto);
    }





    // [TextQuiz][TextChoice] 삭제 + AetResponse 변경 완료
    @DeleteMapping("/{textQuizId}/delete")
    public ResponseEntity<ResBodyModel> deleteTextQuiz(@PathVariable("textQuizId") int textQuizId) {
        // 텍스트 퀴즈 삭제 서비스 호출
        textQuizService.deleteTextQuiz(textQuizId);

        // 삭제 성공을 응답으로 반환
        return AetResponse.toResponse(SuccessCode.SUCCESS, null);
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

