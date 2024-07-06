package sber.spring.Mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MyController {

    @GetMapping("/")
    public String myPage() {
        return "MyPage";
    }
}
