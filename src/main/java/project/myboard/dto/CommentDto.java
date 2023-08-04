package project.myboard.dto;

import lombok.*;
import net.bytebuddy.asm.Advice;
import project.myboard.entity.BoardEntity;
import project.myboard.entity.CommentEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String commentWriter;
    private String commentContent;
    private Long boardId;
    private BoardEntity board;
    private LocalDateTime createdTime;

    @Builder
    public CommentDto(Long id, String commentWriter, String commentContent, BoardEntity board, Long boardId, LocalDateTime createdTime){
        this.id = id;
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
        this.boardId = boardId;
        this.board = board;
        this.createdTime = createdTime;
    }

    public CommentEntity toEntity(BoardEntity boardEntity){
        return CommentEntity.builder()
                .id(id)
                .writer(commentWriter)
                .content(commentContent)
                .board(boardEntity)
                .build();
    }

}
