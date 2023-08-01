package project.myboard.entity;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.myboard.dto.CommentDto;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String writer;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    // foreign key 설정하는 것
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private BoardEntity board;

    @Builder
    public CommentEntity(Long id, String writer, String content, BoardEntity board){
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.board = board;
    }

}
