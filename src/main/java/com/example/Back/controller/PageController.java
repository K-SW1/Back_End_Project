package com.example.Back.controller;

import com.example.Back.service.PageService;
import com.example.Back.service.TextQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    @Autowired
    private TextQuizService textQuizService;
    @Autowired
    private PageService quizService;

    //메인 화면
    @GetMapping("/quiz")
    public String quizMainPage() {
        return "mainpage";
    }

    //Text or image 선택 페이지로 이동
    @GetMapping("/quiz/quizs")
    public String createQuiz() {
        return "createquiz";
    }

    //Text or image 선택 시작
    @GetMapping("/quiz/play")
    public String playQuiz() {
        return "playquiz";
    }

    //텍스트 게임 화면
    @GetMapping("/quiz/play/textquizGame")
    public String startTextQuizGame() {
        return "starttextquizGame";
    }

}

//CRUD 선택 화면 이동
//    @GetMapping("/quiz/quizs/textquiz")
//    public String crudSelection(Model model) {
//        model.addAttribute("quizzes", textQuizService.getAllTextQuizzes());
//        return "crudselection"; // crudselection.html을 찾아서 렌더링
//    }


//문제 또는 선택지 선택 화면으로 이동
//    @GetMapping("/quiz/quizs/textquiz/add")
//    public String questionOrChoiceForm() {
//        return "questionorchoice";
//    }
// }
