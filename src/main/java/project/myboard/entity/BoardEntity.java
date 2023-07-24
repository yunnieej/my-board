package project.myboard.entity;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;
import project.myboard.dto.BoardRequestDto;
import project.myboard.dto.BoardUpdateDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity // db에 있는 table임을 명시함
@Getter
@Table(name="board")
public class BoardEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String writer;

    @NotNull
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column
    private int hits;

    @Column
    private Long fileId;

    @Builder
    public BoardEntity(Long id, String writer, String title, String content, Long fileId, int hits){
        this.id  = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.fileId = fileId;
        this.hits = hits;
    }

    public void update(BoardUpdateDto boardUpdateDto){
        this.title = boardUpdateDto.getTitle();
        this.content = boardUpdateDto.getContent();
        this.hits = this.hits-1;
    }

    public void updateHits(){
        this.hits = this.hits+1;
    }

//    public void minusHits(){
//        this.hits = this.hits-1;
//    }

}