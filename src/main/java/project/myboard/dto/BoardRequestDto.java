package project.myboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.myboard.entity.BoardEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BoardRequestDto {


    @NotBlank(message = "작성자는 필수 입력 항목입니다.")
    private String writer;


    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    @Pattern(regexp="[ /[\\d\\r\\n]/gㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9.,;]+", message="내용에 '.' ',' ';' 이외의 특수문자는 입력할 수 없습니다.")
    private String content;

    private int hits;

    private Long fileId;

    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    @Builder
    public BoardRequestDto(String writer, String title, String content, Long fileId, LocalDateTime createdTime, LocalDateTime modifiedTime){
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.fileId = fileId;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .writer(writer)
                .title(title)
                .fileId(fileId)
                .content(content)
                .hits(0)
                .build();
    }
}