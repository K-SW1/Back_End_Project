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
import java.awt.*;
import lombok.Data;
import org.springframework.ui.Model;


@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/quizs/textquiz")
public class TextQuizController {

    @Autowired
    private final TextQuizService textQuizService;

//조회
    // [TextQuiz](힌트) 조회
    @GetMapping("/all")
    public List<TextQuiz> getAllTextQuizzes() {
        return textQuizService.getAllTextQuizzes();
    }

    // [TextQuiz](선택지)(정답) 조회
    @GetMapping("/{textQuizId}/choices")
    public ResponseEntity<List<Map<String, Object>>> getTextChoicesByQuizId(@PathVariable int textQuizId) {
        List<Map<String, Object>> textChoices = textQuizService.getTextChoicesByQuizId(textQuizId);
        return ResponseEntity.ok().body(textChoices);
    }

//추가    
    // [TextQuiz](힌트) 추가
    @PostMapping("/add")
    public TextQuiz addTextQuiz(@RequestBody TextQuiz textQuiz) {
        return textQuizService.addTextQuiz(textQuiz);
    }
//    {
//        "question": "좋아하는 계절은?",
//            "hint": "눈이 내린다."
//    }

    // [TextQuiz](선택지)(정답) 추가
    @PostMapping("/{textQuizId}/choices/add") //choices변수에 요청 body 값 List<TextChoice> 형태의 choices 변수에 매핑
    public List<TextChoice> addTextChoicesToQuiz(@PathVariable("textQuizId") int textQuizId,
                                                 @RequestBody List<TextChoice> choices) {
        return textQuizService.addTextChoicesToQuiz(textQuizId, choices);
    }
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

//삭제
    // [TextQuiz][TextChoice] 삭제
    @DeleteMapping("/{textQuizId}/delete")
    public ResponseEntity<?> deleteTextQuiz(@PathVariable("textQuizId") int textQuizId) {
        textQuizService.deleteTextQuiz(textQuizId);
        return ResponseEntity.ok().build();
    }


//수정
    // [TextQuiz] 수정 컨트롤러
    @PutMapping("/{textQuizId}/update")
    public ResponseEntity<TextQuiz> updateTextQuiz(@PathVariable int textQuizId,
                                                   @RequestBody TextQuiz updatedTextQuiz) {
        TextQuiz updatedQuiz = textQuizService.updateTextQuiz(textQuizId, updatedTextQuiz);
        return ResponseEntity.ok().body(updatedQuiz);
    }

    // [TextQuiz] 선택지 수정 컨트롤러
    @PutMapping("/{textQuizId}/choices/update")
    public ResponseEntity<List<TextChoice>> updateTextChoices(@PathVariable int textQuizId,
                                                              @RequestBody List<TextChoice> updatedChoices) {
        List<TextChoice> updatedChoicesList = textQuizService.updateTextChoices(textQuizId, updatedChoices);
        return ResponseEntity.ok().body(updatedChoicesList);
    }


}


