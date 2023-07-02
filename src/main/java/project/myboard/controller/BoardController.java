package project.myboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    // 메인화면
    @GetMapping("/")
    public String lists(){
        return "board/board.html";
    }

    // 글쓰는 화면
    @GetMapping("/post")
    public String post(){
        return "board/post.html";
    }
}
