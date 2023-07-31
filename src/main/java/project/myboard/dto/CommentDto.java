package project.myboard.dto;

import lombok.*;
import project.myboard.entity.BoardEntity;
import project.myboard.entity.CommentEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDto {

    private String commentWriter;
    private String commentContent;
    private Long boardId;
    private BoardEntity board;

    @Builder
    public CommentDto(String commentWriter, String commentContent, BoardEntity board, Long boardId){
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
        this.boardId = boardId;
        this.board = board;
    }



}
