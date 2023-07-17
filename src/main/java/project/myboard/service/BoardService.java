package project.myboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
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
        if(boardRequestDto.getWriter().isBlank() || boardRequestDto.getTitle().isBlank()){
            throw new IllegalStateException("필수 입력값 (작성자, 제목)값이 없습니다.");
        }
        else if(boardRequestDto.getContent().matches("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]")){
            throw new IllegalStateException("내용에는 특수문자가 들어갈 수 없습니다.");
        }

        boardRepository.save(boardRequestDto.toEntity());
    }

    /***
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
    */
    @Transactional()
    public Page<BoardResponseDto> findPage(Pageable pageable){
        int page = (pageable.getPageNumber() == 0 ? 0 : (pageable.getPageNumber()-1));

        Page<BoardEntity> all = boardRepository.findAll(PageRequest.of(page, 3, Sort.Direction.DESC, "id"));
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

    /***
    //keyword로 검색
    @Transactional
    public List<BoardResponseDto> searchByKeyword(String keyword){
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        ArrayList<BoardResponseDto> boardResponseDtos = new ArrayList<>();
        // dto로 변환해서 arrayList에 넣는 작업
        for (BoardEntity board : boardEntities){

            BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                    .id(board.getId())
                    .writer(board.getWriter())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdTime(board.getCreatedTime())
                    .modifiedTime(board.getModifiedTime())
                    .hits(board.getHits())
                    .build();

            boardResponseDtos.add(boardResponseDto); // 변환된 것 하나씩 넣기
        }
        return boardResponseDtos;

    }
*/
    @Transactional
    public Page<BoardResponseDto> searchByKeyword(String keyword, Pageable pageable){
        // pageable의 페이지 -> 0부터 시작. 사용자가 보려는 페이지에서 1 빼야함. (게시판 첫페이지는 1부터 시작하기 때문에) page=2면 게시판에서는 첫페이지
        int page = (pageable.getPageNumber() == 0 ? 0 : (pageable.getPageNumber()-1));
        Page<BoardEntity> byTitleContainingPage = boardRepository.findByTitleContaining(keyword, PageRequest.of(page, 3, Sort.Direction.DESC, "id"));

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


//    @Transactional
//    // 게시글 작성 시 유효성 체크하기
//    public Map<String, String> validateHandling(Errors errors){
//        Map<String, String> validatorResult = new HashMap<>();
//
//        for(FieldError error : errors.getFieldErrors()){
//            String validKeyName = String.format("valid_%s", error.getField());
//            validatorResult.put(validKeyName, error.getDefaultMessage());
//        }
//        return validatorResult;
//    }
}
