package project.myboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.myboard.dto.CommentDto;
import project.myboard.entity.CommentEntity;
import project.myboard.service.CommentService;

import javax.transaction.Transactional;
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

    /***
    @PostMapping("/save")
    public @ResponseBody List<CommentDto> save(@ModelAttribute CommentDto commentDto, Model model){
        Long savedId = commentService.saveComment(commentDto);
        System.out.println("savedId :" + savedId); //comment의 id값
        System.out.println(commentDto);

        if (savedId != null){
            List<CommentDto> allComments = commentService.findAllComments(commentDto.getBoardId());
            System.out.println("댓글 개수 :" + allComments.size());
            return allComments;
        }else{
            return null;
        }
    }
    */
}
