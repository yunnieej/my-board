package project.myboard.entity;

import com.fasterxml.jackson.databind.ser.Serializers;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


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

}
