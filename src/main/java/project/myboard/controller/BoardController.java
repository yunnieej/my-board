package project.myboard.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.myboard.dto.BoardDto;
import project.myboard.service.BoardService;

import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 메인화면, 게시판 리스트들을 볼 수 있음
    @GetMapping("/")
    public String list(Model model, @PageableDefault(page = 0, size = 5, sort="id", direction= Sort.Direction.DESC) Pageable pageable){
        List<BoardDto> boardDtoList = boardService.findAll(pageable);
        model.addAttribute("boardList", boardDtoList);
        return "board/list.html";
    }

    // 글쓰는 화면
    @GetMapping("/post")
    public String post(){
        return "board/post.html";
    }

    @PostMapping("/post")
    public String save(@ModelAttribute BoardDto boardDto){
        boardService.saveBoard(boardDto);
        return "redirect:/";
    }

    // 상세 게시글 보기
    @GetMapping("/post/{id}")
    public String findById(@PathVariable Long id, Model model){
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardDto", boardDto);
        return "board/detail.html";
    }

    // id에 해당하는 게시글을 가져온 후 수정
    @GetMapping("/post/update/{id}")
    public String update(@PathVariable Long id, Model model){
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardDto", boardDto);
        return "board/update.html";
    }

    // 게시글 수정 데이터
    @PutMapping("/post/update/{id}")
    public String update(Long id, BoardDto boardDto){
        boardService.update(id, boardDto);

        return "redirect:/post/{id}";
    }


//    @GetMapping("/post/{id}/delete")
    @GetMapping("/post/delete/{id}")
    public String updateBoard(@PathVariable Long id){
        boardService.deleteById(id);
        return "redirect:/";
    }

}
