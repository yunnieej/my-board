package project.myboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import project.myboard.common.PagingConst;
import project.myboard.dto.BoardRequestDto;
import project.myboard.dto.BoardResponseDto;
import project.myboard.dto.BoardUpdateDto;
import project.myboard.entity.BoardEntity;
import project.myboard.repository.BoardRepository;

import javax.transaction.Transactional;
import java.util.*;

// service 단에서 entity로 바꿔줘야함
@Service
@RequiredArgsConstructor // 생성자 생성
public class BoardService {

    // repository -> entity 클래스를 받음
    private final BoardRepository boardRepository;

    // 저장된 boardDto를 데이터베이스에 넣어야하기 때문에 entity로 바꿔야하는 과정 거쳐야함

    @Transactional
    public void saveBoard(BoardRequestDto boardRequestDto){
        boardRepository.save(boardRequestDto.toEntity());
    }

    // pageable 전 전체 리스트 보기
    @Transactional
    public List<BoardResponseDto> findAll(){
        // 여기서 사용하는 findAll() -> SimpleJpaRepository를 사용
        List<BoardEntity> boardEntityList = boardRepository.findAll(); // boardList 가져오기 (Entity 형태)

        ArrayList<BoardResponseDto> boardDtoList = new ArrayList<>(); // boardDto 로 변환하기

        // dto로 변환해서 arrayList에 넣는 작업
        for (BoardEntity board : boardEntityList){

            BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                    .id(board.getId())
                    .writer(board.getWriter())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdTime(board.getCreatedTime())
                    .modifiedTime(board.getModifiedTime())
                    .hits(board.getHits())
                    .build();

            boardDtoList.add(boardResponseDto); // 변환된 것 하나씩 넣기
        }
        return boardDtoList;
    }

    // pageable 후 전체 리스트 보기
    @Transactional()
    public Page<BoardResponseDto> findPage(Pageable pageable){
        int page = (pageable.getPageNumber() == 0 ? 0 : (pageable.getPageNumber()-1));

        Page<BoardEntity> all = boardRepository.findAll(PageRequest.of(page, 2, Sort.Direction.DESC, "id"));
        // new PageImpl<BoardResponseDto>(list, PageRequest.of(currentPage, pageSize), all.size());
        Page<BoardResponseDto> allDto = all.map(m -> BoardResponseDto.builder()
                .id(m.getId())
                .writer(m.getWriter())
                .title(m.getTitle())
                .content(m.getContent())
                .createdTime(m.getCreatedTime())
                .modifiedTime(m.getModifiedTime())
                .hits(m.getHits())
                .build());

        return allDto;
    }

    // 상세 게시글 보기
    @Transactional
    public BoardResponseDto findById(Long id){
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);

        if (!optionalBoardEntity.isPresent()){
            throw new NoSuchElementException("해당 순번이 존재하지 않습니다.");
        }

        BoardEntity boardEntity = optionalBoardEntity.get();
        boardEntity.updateHits();
        BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .writer(boardEntity.getWriter())
                .content(boardEntity.getContent())
                .fileId(boardEntity.getFileId())
                .createdTime(boardEntity.getCreatedTime())
                .modifiedTime(boardEntity.getModifiedTime())
                .hits(boardEntity.getHits())
                .build();

        return boardResponseDto;
    }


    // 상세 게시글 보기
    @Transactional
    public BoardResponseDto findByUpdateId(Long id){
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);

        if (!optionalBoardEntity.isPresent()){
            throw new NoSuchElementException("해당 순번이 존재하지 않습니다.");
        }

        BoardEntity boardEntity = optionalBoardEntity.get();

        BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .writer(boardEntity.getWriter())
                .content(boardEntity.getContent())
                .createdTime(boardEntity.getCreatedTime())
                .modifiedTime(boardEntity.getModifiedTime())
                .hits(boardEntity.getHits())
                .build();

        return boardResponseDto;
    }

    // 수정
    @Transactional
    public void update(Long id, BoardUpdateDto boardUpdateDto){
        BoardEntity boardEntity = boardRepository.findById(id).get();
        boardEntity.update(boardUpdateDto);
    }

    // 삭제
    @Transactional
    public void deleteById(Long id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public Page<BoardResponseDto> searchByKeyword(String keyword, Pageable pageable){
        // pageable의 페이지 -> 0부터 시작. 사용자가 보려는 페이지에서 1 빼야함.
        // 페이지를 0부터 관리하기 때문에 1페이지를 요청하려면 0을, 4페이지를 요청하려면 3을 요청해야합니다.
        int page = (pageable.getPageNumber() == 0 ? 0 : (pageable.getPageNumber()-1));

        Page<BoardEntity> byTitleContainingPage = boardRepository.findByTitleContaining(keyword, PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.Direction.DESC, "id"));

        Page<BoardResponseDto> allDto = byTitleContainingPage.map(m -> BoardResponseDto.builder()
                .id(m.getId())
                .writer(m.getWriter())
                .title(m.getTitle())
                .content(m.getContent())
                .createdTime(m.getCreatedTime())
                .modifiedTime(m.getModifiedTime())
                .hits(m.getHits())
                .build());

        return allDto;
    }

}
