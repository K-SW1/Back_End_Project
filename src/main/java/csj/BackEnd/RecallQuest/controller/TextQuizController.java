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


@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/quizs/textquiz")
public class TextQuizController {

    @Autowired
    private final TextQuizService textQuizService;


    //추가) 특정 텍스트 퀴즈 조회



// [TextQuiz](힌트) 추가
    @PostMapping("/add")
    public ResponseEntity<TextQuizResponseDto> addTextQuiz(@RequestBody TextQuizRequestDto requestDto) {
        // 요청 DTO를 엔티티로 변환합니다
        TextQuizResponseDto responseDto = textQuizService.addTextQuiz(requestDto);

        // 적절한 상태와 함께 응답 DTO를 반환합니다
        return ResponseEntity.ok().body(responseDto);
    }

// [TextQuiz](선택지)(정답) 추가
    @PostMapping("/{textQuizId}/choices/add")
    public List<TextChoiceResponseDto> addTextChoicesToQuiz(@PathVariable("textQuizId") int textQuizId,
                                                            @RequestBody List<TextChoice> choices) {
        // 2. TextChoice 엔티티를 TextChoiceRequestDto로 변환하여 리스트 생성 - 변환 된 DTO객체를 리스트로 모아서 반환
        List<TextChoiceRequestDto> requestDtos = choices.stream()
                .map(this::convertToRequestDto)
                .collect(Collectors.toList());

        // 3. 서비스 메서드 호출 - 텍스트 퀴즈 ID와 선택지 목록을 서비스에 전달 후 데이터베이스에 저장
        List<TextChoice> savedChoices = textQuizService.addTextChoicesToQuiz(textQuizId, requestDtos);

        // 4. 서비스에서 반환된 엔티티를 응답 DTO로 변환하여 반환 .map 중간연사자로 새로운 스트림 만듬 .collect이걸로 스트림 요소 리스트 수집하고 반환
        return savedChoices.stream()
                .map(choice -> {
                    TextChoiceResponseDto responseDto = new TextChoiceResponseDto();
                    responseDto.setChoiceText(choice.getChoiceText());
                    responseDto.setAnswer(choice.isAnswer());
                    return responseDto;
                })
                .collect(Collectors.toList());
    }
    // 1. TextChoice 엔티티를 TextChoiceRequestDto로 변환하는 메서드
    private TextChoiceRequestDto convertToRequestDto(TextChoice choice) {
        TextChoiceRequestDto requestDto = new TextChoiceRequestDto();
        requestDto.setChoiceText(choice.getChoiceText());
        requestDto.setAnswer(choice.isAnswer());
        return requestDto;
    }





// [TextQuiz](힌트) 조회
    @GetMapping("/all")
    public List<TextQuizResponseDto> getAllTextQuizzes() {
        List<TextQuiz> textQuizzes = textQuizService.getAllTextQuizzes();

        // TextQuiz 엔티티를 TextQuizResponseDto로 변환하여 반환
        return textQuizzes.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    // TextQuiz 엔티티를 TextQuizResponseDto로 변환하는 메서드
    private TextQuizResponseDto convertToResponseDto(TextQuiz textQuiz) {
        TextQuizResponseDto responseDto = new TextQuizResponseDto();
        responseDto.setTextQuizId(textQuiz.getTextQuizId());
        responseDto.setMemberInfoId(textQuiz.getMemberInfoId());
        responseDto.setQuestion(textQuiz.getQuestion());
        responseDto.setHint(textQuiz.getHint());
        return responseDto;
    }


// [TextQuiz](선택지)(정답) 조회
    @GetMapping("/{textQuizId}/choices")
    public List<TextChoiceResponseDto> getTextChoicesByQuizId(@PathVariable("textQuizId") int textQuizId) {
        // 텍스트 퀴즈 ID에 해당하는 선택지를 가져와서 반환
        return textQuizService.getTextChoicesByQuizId(textQuizId);
    }






// [TextQuiz] 수정 컨트롤러
    @PutMapping("/{textQuizId}/update")
    public ResponseEntity<TextQuizResponseDto> updateTextQuiz(@PathVariable int textQuizId,
                                                              @RequestBody TextQuizRequestDto updatedTextQuizRequestDto) {
        TextQuizResponseDto updatedQuiz = textQuizService.updateTextQuiz(textQuizId, updatedTextQuizRequestDto);
        return ResponseEntity.ok().body(updatedQuiz);
    }

// [TextQuiz](선택지) 수정 컨트롤러
    @PutMapping("/{textQuizId}/choices/update")
    public ResponseEntity<List<TextChoiceResponseDto>> updateTextChoices(@PathVariable int textQuizId,
                                                                         @RequestBody List<TextChoiceRequestDto> updatedChoicesRequestDto) {
        // 서비스 메서드 호출
        List<TextChoiceResponseDto> updatedChoicesResponseDto = textQuizService.updateTextChoices(textQuizId, updatedChoicesRequestDto);
        return ResponseEntity.ok().body(updatedChoicesResponseDto);
    }




    // [TextQuiz][TextChoice] 삭제
    @DeleteMapping("/{textQuizId}/delete")
    public ResponseEntity<?> deleteTextQuiz(@PathVariable("textQuizId") int textQuizId) {
        textQuizService.deleteTextQuiz(textQuizId);
        return ResponseEntity.ok().build();
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

