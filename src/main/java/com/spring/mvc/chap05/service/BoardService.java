package com.spring.mvc.chap05.service;


import com.spring.mvc.chap05.dto.request.BoardWriteRequestDTO;
import com.spring.mvc.chap05.dto.response.BoardDetailResponseDTO;
import com.spring.mvc.chap05.dto.response.BoardListResponseDTO;
import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // 빈 등록, 컨트롤러가 필요하는 서비스, 레파지토리와 의존 관계인 서비스
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository repository;

    public void register(BoardWriteRequestDTO dto) {
        // 값을 정제하고 entity로 정제한다.
        // dto(writer, title, content) 객체가 지니는 필드를 entity(Board)로 정제한다.
        // Board 클래스에 BoardWriteRequestDTO dto 를 매개변수로 받는 생성자를 생성한다.
        // 값을 정제하는 것은 서비스단에서 한다.
        Board board = new Board(dto);
        repository.save(board);
    }

    // repository로부터 전달받은 entity List를 DTO List로 변환해서 컨트롤러에게 전달한다.
    public List<BoardListResponseDTO> getList() {
        List<BoardListResponseDTO> dtoList = new ArrayList<>();

        List<Board> boardList = repository.findAll();


        for (Board board : boardList) {
            BoardListResponseDTO dto = new BoardListResponseDTO(board);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public BoardDetailResponseDTO getDetail(int bno) {
        // 상세보기니까 조회수를 하나 올려주는 처리를 해야 한다.
        // 레파지토리쪽에 조회수를 하나 올리는 메서드를 작성해야 한다.
        // 조회수를 먼저 올리는 게 순서다.
        // 조회수를 먼저 올리고, 해당 데이터를 모두 갖고 있는 객체를 가져온다.
        repository.updateViewCount(bno);

        Board board = repository.findOne(bno);
        return new BoardDetailResponseDTO(board);
    }

    public void delete(int bno) {
        repository.delete(bno);
    }
}
