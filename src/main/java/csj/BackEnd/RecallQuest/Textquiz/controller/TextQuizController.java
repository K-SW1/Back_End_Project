package csj.BackEnd.RecallQuest.Textquiz.controller;


import csj.BackEnd.RecallQuest.entity.TextQuiz;
import csj.BackEnd.RecallQuest.Textquiz.service.TextQuizService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import java.util.List;

import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizRequestDto;
import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizResponseDto;


import java.util.stream.Collectors;

import csj.BackEnd.RecallQuest.common.KsResponse;
import csj.BackEnd.RecallQuest.common.code.SuccessCode;
import csj.BackEnd.RecallQuest.common.model.ResBodyModel;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/quizs/textquiz")
public class TextQuizController {



    private final TextQuizService textQuizService;



    // [TextQuiz](힌트) 추가  + AetResponse 변경 완료
    @PostMapping("/add")
    public ResponseEntity<ResBodyModel> addTextQuiz(@RequestBody TextQuizRequestDto requestDto) {
        // 요청 DTO를 엔티티로 변환합니다
        TextQuizResponseDto responseDto = textQuizService.addTextQuiz(requestDto);

        // 적절한 상태와 함께 응답 DTO를 반환합니다
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDto);
    }






    // [TextQuiz](힌트) 조회 + AetResponse 변경 완료
    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> getAllTextQuizzes() {
        List<TextQuiz> textQuizzes = textQuizService.getAllTextQuizzes();

        List<TextQuizResponseDto> responseDtos = textQuizzes.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        // AetResponse를 사용하여 ResponseEntity를 생성
        return KsResponse.toResponse(SuccessCode.SUCCESS, responseDtos);
    }
    private TextQuizResponseDto convertToResponseDto(TextQuiz textQuiz) {
        TextQuizResponseDto responseDto = new TextQuizResponseDto();
        responseDto.setTextQuizId(textQuiz.getTextQuizId());
        responseDto.setMember(  textQuiz.getMember());
        responseDto.setQuestion(textQuiz.getQuestion());
        responseDto.setHint(textQuiz.getHint());
        return responseDto;
    }


    // [TextQuiz](힌트) 특정 조회
    @GetMapping("/{textQuizId}")
    public ResponseEntity<ResBodyModel> getTextQuizById(@PathVariable int textQuizId) {
        TextQuizResponseDto textQuiz = textQuizService.getTextQuizById(textQuizId);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return KsResponse.toResponse(SuccessCode.SUCCESS, textQuiz);
    }




    // [TextQuiz] 수정 컨트롤러 + AetResponse 변경 완료
    @PutMapping("/{textQuizId}/update")
    public ResponseEntity<ResBodyModel> updateTextQuiz(@PathVariable int textQuizId,
                                                       @RequestBody TextQuizRequestDto updatedTextQuizRequestDto) {
        TextQuizResponseDto updatedQuiz = textQuizService.updateTextQuiz(textQuizId, updatedTextQuizRequestDto);

        // AetResponse를 사용하여 ResponseEntity를 생성
        return KsResponse.toResponse(SuccessCode.SUCCESS, updatedQuiz);
    }






    // [TextQuiz][TextChoice] 삭제 + AetResponse 변경 완료
    @DeleteMapping("/{textQuizId}/delete")
    public ResponseEntity<ResBodyModel> deleteTextQuiz(@PathVariable("textQuizId") int textQuizId) {
        // 텍스트 퀴즈 삭제 서비스 호출
        textQuizService.deleteTextQuiz(textQuizId);

        // 삭제 성공을 응답으로 반환
        return KsResponse.toResponse(SuccessCode.SUCCESS, null);
    }



}
//
//    {
//        "question": "좋아하는 계절은?",
//            "hint": "눈이 내린다."
//    }
//

//[
//  {
//   "textzQuizDistractor": "봄",
//   "answer": false
//   },
//   {
//   "textzQuizDistractor": "여름",
//   "answer": false
//    },
//    {
//    "textzQuizDistractor": "가을",
//     "answer": false
//    },
//    {
//      "textzQuizDistractor": "겨울",
//      "answer": true
//    }
//]

//spring.jpa.hibernate.ddl-auto=create DB생성 코드