package project.myboard.dto;

import lombok.*;
import net.bytebuddy.asm.Advice;
import project.myboard.entity.BoardEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String writer;
    private String password;
    private String title;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    // toEntity 정의해서 entity로 바꿀 수 있음
    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .password(password)
                .title(title)
                .content(content)
                .build();

        return boardEntity;
    }


    @Builder
    public BoardDto(Long id, String writer, String password, String title, String content, LocalDateTime createdTime,
                    LocalDateTime modifiedTime){
        this.id = id;
        this.writer = writer;
        this.password = password;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

}
