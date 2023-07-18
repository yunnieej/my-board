package project.myboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BoardUpdateDto {

    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    private String title;

    @Pattern(regexp="[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]*",message="내용에 특수문자는 입력할 수 없습니다.")
    private String content;

    private LocalDateTime modifiedTime;
}

