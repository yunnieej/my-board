package project.myboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.myboard.dto.CommentDto;
import project.myboard.entity.CommentEntity;
import project.myboard.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/save")
    public @ResponseBody void save(@ModelAttribute CommentDto commentDto){
        Long savedId = commentService.saveComment(commentDto);
        System.out.println("savedId :" + savedId); //comment의 id값
        System.out.println(commentDto);

        if (savedId != null){
            List<CommentEntity> allComments = commentService.findAllComments(commentDto.getBoardId());

        }
    }
}
