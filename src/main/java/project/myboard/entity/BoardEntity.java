package project.myboard.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    private Long id;

    // 작성자
    @Column
    private String writer;

    // 비밀번호
    @Column
    private String password;

    // 제목
    @Column(length = 500, nullable = false)
    private String title;

    // 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 조회수
    @Column
    private int view;


    @Builder
    public BoardEntity(Long id, String writer, String password, String title, String content, int view) {
        this.id = id;
        this.writer = writer;
        this.password = password;
        this.title = title;
        this.content = content;
        this.view = view;
    }
}
