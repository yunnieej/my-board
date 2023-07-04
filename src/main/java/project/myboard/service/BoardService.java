package project.myboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.myboard.dto.BoardDto;
import project.myboard.entity.BoardEntity;
import project.myboard.repository.BoardRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// service 단에서 entity로 바꿔줘야함
@Service
@RequiredArgsConstructor // 생성자 생성
public class BoardService {

    // repository -> entity 클래스를 받음
    private final BoardRepository boardRepository;

    // requiredAgrsConstructor 을 하면 생성자 자동 생성해서 아래 코드 없어도 됨.
//    public BoardService(BoardRepository boardRepository) {
//        this.boardRepository = boardRepository;
//    }

    // 저장된 boardDto를 데이터베이스에 넣어야하기 때문에 entity로 바꿔야하는 과정 거쳐야함
    @Transactional
    public void saveBoard(BoardDto boardDto){
        BoardEntity boardEntity = boardDto.toEntity();
        boardRepository.save(boardEntity);
//        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public Page<BoardDto> findAll(Pageable pageable){
        Page<BoardEntity> boardEntityList = boardRepository.findAll(pageable); // boardList 가져오기 (Entity 형태)
//        System.out.println("findAll() 가져오기" + boardEntityList);
//        System.out.println(boardEntityList.size());
        ArrayList<BoardDto> boardDtoList = new ArrayList<>(); // boardDto 로 변환하기

        // dto로 변환해서 arrayList에 넣는 작업
        for (BoardEntity board : boardEntityList){

            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .writer(board.getWriter())
                    .password(board.getPassword())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdTime(board.getCreatedTime())
                    .modifiedTime(board.getModifiedTime())
                    .build();

            boardDtoList.add(boardDto); // 변환된 것 하나씩 넣기
        }

//        return boardDtoList;
        return new PageImpl<>(boardDtoList, pageable, boardEntityList.getTotalElements());
    }

    @Transactional
    public BoardDto findById(Long id){
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        BoardEntity boardEntity = optionalBoardEntity.get();
        if (optionalBoardEntity.isPresent()){
            BoardDto boardDto = BoardDto.builder()
                    .id(boardEntity.getId())
                    .writer(boardEntity.getWriter())
                    .password(boardEntity.getPassword())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .createdTime(boardEntity.getCreatedTime())
                    .modifiedTime(boardEntity.getModifiedTime())
                    .build();

            return boardDto;
        }
        else{
            return null;
        }
    }

    // 수정
    @Transactional
    public void update(Long id, BoardDto boardDto){

        BoardEntity boardEntity = boardRepository.findById(id).get().builder()
                .id(boardDto.getId())
                .writer(boardDto.getWriter())
                .password(boardDto.getPassword())
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .build();

        boardEntity.updateBoard(boardDto);
        boardRepository.save(boardEntity);

    }

    // 삭제
    @Transactional
    public void deleteById(Long id){
        boardRepository.deleteById(id);
    }
}
