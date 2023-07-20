package project.myboard.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.myboard.dto.BoardRequestDto;
import project.myboard.dto.BoardResponseDto;
import project.myboard.dto.BoardUpdateDto;
import project.myboard.service.BoardService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class BoardController {
    private final BoardService boardService;
    public BoardController(BoardService boardService) {this.boardService = boardService;}


    // 메인화면, 게시판 리스트들을 볼 수 있음
    @GetMapping("/")
    public String list(Model model){
//        List<BoardResponseDto> boardDtoList = boardService.findAll();
//        model.addAttribute("boardList", boardDtoList);
        return "redirect:/search?page=1&keyword=";
    }

    // 메인화면, 게시판 리스트들을 볼 수 있음(Pageable 후)
//    @GetMapping("/")
//    public String list(@PageableDefault Pageable pageable, Model model){
//        Page<BoardResponseDto> page = boardService.findPage(pageable);
//        model.addAttribute("boardList", page);
//        return "redirect:/search?page=1&keyword=";
////        return "board/list";
//    }

    // 글 작성하는 화면
    @GetMapping("/post")
    public String post(Model model){
        model.addAttribute("boardRequestDto", new BoardRequestDto());
        return "board/post.html";
    }

    // 글 작성
    @PostMapping("/post")
    public String save(@Valid BoardRequestDto boardRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "board/post.html";
        }
        boardService.saveBoard(boardRequestDto);
        return "redirect:/";
    }

    // 상세 게시글 보기
    @GetMapping("/post/{id}")
    public String findById(@PathVariable Long id, Model model){
//                           @RequestParam(value="page") String page, @RequestParam(value="keyword") String keyword){
        BoardResponseDto boardResponseDto = boardService.findById(id);

//        System.out.println("윤지의 페이지2 ======  "  + page);
//        System.out.println("윤지의 페이지2 ======  "  + keyword);
//        model.addAttribute("page",page);
//        model.addAttribute("keyword",keyword);
        model.addAttribute("boardDto", boardResponseDto);
        return "board/detail.html";
    }

    // id에 해당하는 게시글을 가져와 보여줌
    @GetMapping("/post/update/{id}")
    public String update(@PathVariable Long id, Model model){
        BoardResponseDto boardResponseDto = boardService.findById(id);
        model.addAttribute("boardDto", boardResponseDto);
        return "board/update.html";
    }

    // 게시글 수정 데이터
    @PutMapping("/post/update/{id}")
    public String update(HttpServletRequest request, RedirectAttributes redirectAttributes, BoardUpdateDto boardUpdateDto, Long id){
        redirectAttributes.addAttribute("page", request.getParameter("page"));
        redirectAttributes.addAttribute("keyword", request.getParameter("keyword"));
//        System.out.println("윤지와 페이지 === " + request.getParameter("page"));
//        System.out.println("키워드 출력 ==="+ request.getParameter("keyword"));
        boardService.update(id, boardUpdateDto);
        return "redirect:/post/{id}";
    }

    //게시글 삭제
    @GetMapping("/post/delete/{id}")
    public String updateBoard(@PathVariable Long id){
        boardService.deleteById(id);
        return "redirect:/";
    }

    //게시글 검색기능
//    @GetMapping("/search")
//    public String searchByKeyword(@RequestParam(value="keyword", required = false) String keyword, Model model){
//        List<BoardResponseDto> boardResponseDtos = boardService.searchByKeyword(keyword);
//        model.addAttribute("boardList", boardResponseDtos);
//        return "board/list.html";
//    }

    @GetMapping("/search")
    public String searchByKeyword(@RequestParam(value="keyword") String keyword, Model model, @PageableDefault Pageable pageable){
        System.out.println("들어온 keyword = "+  keyword);
        System.out.println("들어온 page = " + pageable.getPageNumber());
        model.addAttribute("boardList", boardService.searchByKeyword(keyword, pageable)); //keyword pageable의 page 넘어옴
        return "board/list.html";
    }
}
