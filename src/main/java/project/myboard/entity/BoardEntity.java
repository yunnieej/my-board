package project.myboard.entity;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;
import project.myboard.dto.BoardUpdateDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
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

    @Builder
    public BoardEntity(Long id, String writer, String title, String content, int hits){
        this.id  = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.hits = hits;
    }

    public void update(BoardUpdateDto boardUpdateDto){
        this.title = boardUpdateDto.getTitle();
        this.content = boardUpdateDto.getContent();
    }

    public void updateHits(){
        this.hits = this.hits+1;
    }

    public void minusHits(){
        this.hits = this.hits-1;
    }

}