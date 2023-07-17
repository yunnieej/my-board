package project.myboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String writer;
    private int hits;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    @Builder
    public BoardResponseDto(Long id, String title, String writer, String content ,int hits, LocalDateTime createdTime,
                            LocalDateTime modifiedTime){
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.hits = hits;
        this.content = content;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

}
