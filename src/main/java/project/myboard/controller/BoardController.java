package project.myboard.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.myboard.dto.BoardRequestDto;
import project.myboard.dto.BoardResponseDto;
import project.myboard.dto.BoardUpdateDto;
import project.myboard.service.BoardService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 메인화면, 게시판 리스트들을 볼 수 있음
//    @GetMapping("/")
//    public String list(Model model){
//        List<BoardResponseDto> boardDtoList = boardService.findAll();
//        model.addAttribute("boardList", boardDtoList);
//        return "board/list.html";
//    }

    // 메인화면, 게시판 리스트들을 볼 수 있음
    @GetMapping("/")
    public String list(@PageableDefault Pageable pageable, Model model){
        Page<BoardResponseDto> page = boardService.findPage(pageable);
        model.addAttribute("boardList", page);
        return "board/list.html";
    }

    // 글쓰는 화면
    @GetMapping("/post")
    public String post(){
        return "board/post.html";
    }

    @PostMapping("/post")
    public String save(@Valid BoardRequestDto boardRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "board/post.html";
        }
        boardService.saveBoard(boardRequestDto);
        return "redirect:/";
    }

    //    @PostMapping("/post")
//    public String save(@ModelAttribute BoardDto boardDto){
//        boardService.saveBoard(boardDto);
//        return "redirect:/";
//    }


    // 상세 게시글 보기
    @GetMapping("/post/{id}")
    public String findById(@PathVariable Long id, Model model){
        BoardResponseDto boardResponseDto = boardService.findById(id);
        model.addAttribute("boardDto", boardResponseDto);
        return "board/detail.html";
    }

    // id에 해당하는 게시글을 가져온 후 수정
    @GetMapping("/post/update/{id}")
    public String update(@PathVariable Long id, Model model){
        BoardResponseDto boardResponseDto = boardService.findById(id);
        model.addAttribute("boardDto", boardResponseDto);
        return "board/update.html";
    }

    // 게시글 수정 데이터
    @PutMapping("/post/update/{id}")
    public String update(Long id, BoardUpdateDto boardUpdateDto){
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
//    public String searchByKeyword(@RequestParam(value="keyword") String keyword, Model model){
//        List<BoardResponseDto> boardResponseDtos = boardService.searchByKeyword(keyword);
//        model.addAttribute("boardList", boardResponseDtos);
//        return "board/list.html";
//    }

    @GetMapping("/search")
    public String searchByKeyword(@RequestParam(value="keyword") String keyword, Model model, @PageableDefault(sort = "id", size = 3, page = 1, direction = Sort.Direction.DESC) Pageable pageable){
        Page<BoardResponseDto> boardResponseDtos = boardService.searchByKeyword(keyword, pageable);
        model.addAttribute("boardList", boardResponseDtos);
        return "board/list.html";
    }

}
