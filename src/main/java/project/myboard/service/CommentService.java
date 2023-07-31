package project.myboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.myboard.dto.CommentDto;
import project.myboard.entity.BoardEntity;
import project.myboard.entity.CommentEntity;
import project.myboard.repository.BoardRepository;
import project.myboard.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 생성자 생성
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long saveComment(CommentDto commentDto){
//        commentRepository.save(commentDto.toEntity(commentDto));

        BoardEntity boardEntity = boardRepository.findById(commentDto.getBoardId()).get(); //boardId에 해당하는 boardEntity 가져옴.
        CommentEntity commentEntity = new CommentEntity(commentDto.getCommentWriter(), commentDto.getCommentContent(), boardEntity);
        CommentEntity saveEntity = commentRepository.save(commentEntity);

        return saveEntity.getId();
    }

    public List<CommentEntity> findAllComments(Long boardId){
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> byBoardId = commentRepository.findByBoardId(boardEntity.getId());
        // CommentEntity -> Dto로 변환시키기
        return byBoardId;
    }


}