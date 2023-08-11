package project.myboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import project.myboard.dto.CommentDto;

import project.myboard.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody CommentDto commentDto) throws Exception {

        Long savedId = commentService.saveComment(commentDto);

        if (savedId != null){
            List<CommentDto> allComments = commentService.findAllComments(commentDto.getBoardId());
            return new ResponseEntity<>(allComments, HttpStatus.OK);
        }else{
            return null;
        }
    }

    @GetMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") Long id,
                                     @RequestParam("boardId") Long boardId){
        commentService.deleteComment(id);
        System.out.println("삭제될 id : " + id);
        System.out.println("해당 게시글 id : " + boardId);

        List<CommentDto> allComments = commentService.findAllComments(boardId);
        System.out.println(allComments);
        return new ResponseEntity<>(allComments, HttpStatus.OK);

    }

}
