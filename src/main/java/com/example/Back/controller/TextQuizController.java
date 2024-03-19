package com.example.Back.controller;

import com.example.Back.entity.TextQuiz;
import com.example.Back.entity.TextChoice;
import com.example.Back.service.TextQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.Data;


@RestController
@RequiredArgsConstructor
public class TextQuizController {

    private final TextQuizService textQuizService;


    // 모든 텍스트 퀴즈를 조회 //지금까지 작성 된 모든 퀴즈의 데이터를 객체에 넣어서 보내줌. // model.addAttribute("quizzes", textQuizService.getAllTextQuizzes()); return "listQuizzes";
    @GetMapping("/quiz/quizs/textquizs")
    public List<TextQuiz> getAllTextQuizzes(Model model) { // List<TextQuiz> quizzes = textQuizService.getAllTextQuizzes(); return quizzes;
        return textQuizService.getAllTextQuizzes();
    }
    // 특정 텍스트 퀴즈의 내용을 조회,
    @GetMapping("/quiz/quizs/textquizs/{textQuizId}")
    public TextQuiz getTextQuizById(@PathVariable Long textQuizId) { // TextQuiz textQuiz = textQuizService.getTextQuizByQuizId(textQuizId); return textQuiz;
        return textQuizService.getTextQuizByQuizId(textQuizId);
    }
    // 특정 선택지의 내용을 조회
    @GetMapping("/quiz/quizs/textquizs/{textChoiceId}")
    public TextChoice getTextChoiceById(@PathVariable Long textChoiceId) {
        return textQuizService.getTextChoiceByChoiceId(textChoiceId);
    }


    // 모든 텍스트 퀴즈를 조회
    @GetMapping("/quiz/quizs/updates")
    public List<TextQuiz> getAllTextQuizzesForUpdate(Model model) {
        return textQuizService.getAllTextQuizzes();
    }
    // 특정 텍스트 퀴즈의 내용을 수정
    @PutMapping("/quiz/quizs/updates/{textQuizId}")
    public TextQuiz updateTextQuiz(@PathVariable Long textQuizId, @RequestBody TextQuiz updatedTextQuiz) {
        TextQuiz textQuiz = textQuizService.getTextQuizByQuizId(textQuizId);
        textQuiz.setQuestion(updatedTextQuiz.getQuestion());
        textQuiz.setHint(updatedTextQuiz.getHint());
        return textQuizService.updateTextQuiz(textQuiz);
    }
    // 특정 선택지의 내용을 수정
    @PutMapping("/quiz/quizs/updates/{textChoiceId}")
    public TextChoice updateTextChoice(@PathVariable Long textChoiceId, @RequestBody TextChoice updatedTextChoice) {
        TextChoice textChoice = textQuizService.getTextChoiceByChoiceId(textChoiceId);
        textChoice.setChoice_text(updatedTextChoice.getChoice_text());
        textChoice.setAnswer(updatedTextChoice.isAnswer());
        return textQuizService.updateTextChoice(textChoice);
    }


    // 모든 텍스트 퀴즈를 조회
    @GetMapping("/quiz/quizs/deletes")
    public List<TextQuiz> getAllTextQuizzesForDelete(Model model) {
        return textQuizService.getAllTextQuizzes();
    }
    // 특정 텍스트 퀴즈를 삭제 // 텍스트 퀴즈를 삭제하고 삭제된 후의 텍스트 퀴즈 목록을 반환합니다.
    @DeleteMapping("/quiz/quizs/delete/{textQuizId}")
    public List<TextQuiz> deleteTextQuiz(@PathVariable Long textQuizId) {
        return textQuizService.deleteTextQuiz(textQuizId);
    }
    // 특정 선택지를 삭제
    @DeleteMapping("/quiz/quizs/delete/{textChoiceId}")
    public List<TextChoice> deleteTextChoice(@PathVariable Long textChoiceId) {
        return textQuizService.deleteTextChoice(textChoiceId);
    }


    // 텍스트 퀴즈 문제 추가
    @PostMapping("/quiz/quizs/textquiz/data1")
    public List<TextQuiz> addTextQuizQuestion(@RequestBody TextQuiz question) {
        textQuizService.addTextQuizQuestion(question);
        return textQuizService.getAllTextQuizzes();
    }
    // 텍스트 퀴즈 선택지 추가
    @PostMapping("/quiz/quizs/textquiz/data2")
    public List<TextChoice> addTextQuizQuestion(@RequestBody TextChoice choice_text) {
        textQuizService.addTextQuizChoice(choice_text);
        return textQuizService.getAllTextChoices();
    }
}
