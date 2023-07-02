package project.myboard.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter

public class BaseEntity {

    @Column(updatable = false)
    @CreatedDate // entity가 생성되어 저장될 때의 시간 자동 생성
    private LocalDateTime createdTime;

    @LastModifiedDate // 조회한 entity의 값을 변경할 때의 시간 자동 생성
    private LocalDateTime modifiedTime;
}
