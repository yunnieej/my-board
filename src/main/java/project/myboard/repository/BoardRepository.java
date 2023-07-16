package project.myboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.myboard.entity.BoardEntity;

import java.util.List;

// 데이터의 조작을 담당하고, JpaRepository 상속받음
// Entity와 id 타입과 매핑
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // keyword로 검색기능
    List<BoardEntity> findByTitleContaining(String keyword);
}
