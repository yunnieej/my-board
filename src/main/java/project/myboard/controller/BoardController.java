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
import project.myboard.dto.CommentDto;
import project.myboard.service.BoardService;
import project.myboard.service.CommentService;
import project.myboard.service.FileService;
import javax.validation.Valid;
import java.util.List;

@Controller
public class BoardController {
    private final BoardService boardService;
    private final FileService fileService;
    private final CommentService commentService;

    public BoardController(BoardService boardService, FileService fileService, CommentService commentService) {
        this.boardService = boardService;
        this.fileService = fileService;
        this.commentService = commentService;
    }

    // 메인화면, 게시판 리스트들을 볼 수 있음
    @GetMapping("/")
    public String list(){
        return "redirect:/search?page=1&keyword=";
    }


    // 글 작성하는 화면
    @GetMapping("/post")
    public String post(Model model){
        model.addAttribute("RequestDto", new BoardRequestDto());

        return "board/post.html";
    }

    // 글 작성
    @PostMapping("/post")
    public String save(@ModelAttribute("RequestDto") @Valid BoardRequestDto boardRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "board/post.html";
        }

        boardService.saveBoard(boardRequestDto);

        return "redirect:/";
    }

    // 상세 게시글 보기
    @GetMapping("/post/{id}")
    public String findById(@PathVariable Long id, @RequestParam(value="page") String page, @RequestParam(value="keyword") String keyword, Model model){
        BoardResponseDto boardResponseDto = boardService.findById(id);
        List<CommentDto> allComments = commentService.findAllComments(id);
        model.addAttribute("boardDto", boardResponseDto);
        model.addAttribute("commentList", allComments);
        return "board/detail.html";
    }

    // id에 해당하는 게시글을 가져와 보여줌
    @GetMapping("/post/update/{id}")
    public String update(@PathVariable Long id, Model model){
        BoardResponseDto boardResponseDto = boardService.findByUpdateId(id);
        BoardUpdateDto updateDto = new BoardUpdateDto();
        updateDto.setId(boardResponseDto.getId());
        updateDto.setTitle(boardResponseDto.getTitle());
        updateDto.setWriter(boardResponseDto.getWriter());
        updateDto.setContent(boardResponseDto.getContent());

        model.addAttribute("updateDto", updateDto);

        return "board/update.html";
    }

    // 게시글 수정 데이터
    @PutMapping("/post/update/{id}")
    public String update(@RequestParam(value="page") String page, @RequestParam(value="keyword") String keyword, Model model,
                         RedirectAttributes redirectAttributes, @ModelAttribute("updateDto") @Valid BoardUpdateDto boardUpdateDto, BindingResult bindingResult, Long id){

        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("keyword", keyword);

        if(bindingResult.hasErrors()){
            return "board/update.html";
        }
        boardService.update(id, boardUpdateDto);

        return "redirect:/post/{id}";
    }


    //게시글 삭제
    @GetMapping("/post/delete/{id}")
    public String updateBoard(@PathVariable Long id){
        boardService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchByKeyword(@RequestParam(value="keyword") String keyword, Model model, @PageableDefault Pageable pageable){
        Page<BoardResponseDto> boardList = boardService.searchByKeyword(keyword, pageable);

        model.addAttribute("boardList", boardList); //keyword pageable의 page 넘어옴
        model.addAttribute("boardCount", boardList.getTotalElements());

        return "board/list.html";
    }
}
