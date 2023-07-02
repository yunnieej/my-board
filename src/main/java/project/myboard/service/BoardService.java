package project.myboard.service;

import org.springframework.stereotype.Service;
import project.myboard.dto.BoardDto;
import project.myboard.entity.BoardEntity;
import project.myboard.repository.BoardRepository;

// service 단에서 entity로 바꿔줘야함
@Service
public class BoardService {

    // repository -> entity 클래스를 받음
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void save(BoardDto boardDto){
        BoardEntity boardEntity = boardDto.toEntity();
        boardRepository.save(boardEntity);
    }

}
