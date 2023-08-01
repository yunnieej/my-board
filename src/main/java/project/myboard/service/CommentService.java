package project.myboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.myboard.dto.CommentDto;
import project.myboard.entity.BoardEntity;
import project.myboard.entity.CommentEntity;
import project.myboard.repository.BoardRepository;
import project.myboard.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 생성자 생성
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long saveComment(CommentDto commentDto) throws Exception{
//        commentRepository.save(commentDto.toEntity(commentDto));
        if(commentDto.getCommentWriter() == "" && commentDto.getCommentContent() == ""){
            throw new Exception("작성자나 내용이 존재하지 않습니다.");
        }
        BoardEntity boardEntity = boardRepository.findById(commentDto.getBoardId()).get();
        CommentEntity commentEntity = commentDto.toEntity(boardEntity);
        CommentEntity saveEntity = commentRepository.save(commentEntity);
        /***
        BoardEntity boardEntity = boardRepository.findById(commentDto.getBoardId()).get(); //boardId에 해당하는 boardEntity 가져옴.
        CommentEntity commentEntity = new CommentEntity(commentDto.getCommentWriter(), commentDto.getCommentContent(), boardEntity);
        CommentEntity saveEntity = commentRepository.save(commentEntity);
*/
        return saveEntity.getId();
    }

    @Transactional
    public List<CommentDto> findAllComments(Long boardId){
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> byBoardId = commentRepository.findByBoardId(boardEntity.getId());
        List<CommentDto> byBoardIdDto = new ArrayList<>();

        for(CommentEntity comment : byBoardId){
            CommentDto commentDto = CommentDto.builder()
                    .id(comment.getId())
                    .commentWriter(comment.getWriter())
                    .commentContent(comment.getContent())
                    .boardId(comment.getBoard().getId())
                    .board(comment.getBoard())
                    .build();
            byBoardIdDto.add(commentDto);
        }
        // CommentEntity -> Dto로 변환시키기
        return byBoardIdDto;
    }


}