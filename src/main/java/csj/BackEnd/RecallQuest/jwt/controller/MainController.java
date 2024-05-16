package csj.BackEnd.RecallQuest.jwt.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {

    @GetMapping("/")
    public String mainP() {

        return "Main Controller ";
    }

    @GetMapping("/aa")
    public String mainPa() {

        return "aa";
    }

}
