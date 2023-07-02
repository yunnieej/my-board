package project.myboard.dto;

import lombok.Getter;
import project.myboard.entity.BoardEntity;

@Getter
public class BoardDto {
    private Long id;
    private String writer;
    private String password;
    private String title;
    private String content;
    private String view;


    // toEntity 정의해서 entity로 바꿀 수 있음
    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .password(password)
                .title(title)
                .content(content)
                .view(0)
                .build();

        return boardEntity;
    }

}
