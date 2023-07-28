package project.myboard.entity;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;


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

    // forign key 설정하는 것
    @ManyToOne
    @JoinColumn(name="board_id")
    private BoardEntity board;




}
