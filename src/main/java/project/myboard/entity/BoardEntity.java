package project.myboard.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.myboard.dto.BoardDto;

import javax.persistence.*;

@DynamicInsert
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    private Long id;

    // 작성자
    @Column(nullable = false)
    private String writer;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 제목
    @Column(length = 500, nullable = false)
    private String title;

    // 내용

    @Column(columnDefinition = "TEXT", length=500, nullable = false)
    private String content;

//    // 조회수
//    @Column
//    private int view;


    @Builder
    public BoardEntity(Long id, String writer, String password, String title, String content) {
        this.id = id;
        this.writer = writer;
        this.password = password;
        this.title = title;
        this.content = content;
    }

    public void updateBoard(BoardDto boardDto){
        this.title = title;
        this.password =password;
        this.writer = writer;
        this.content = content;

    }
}
