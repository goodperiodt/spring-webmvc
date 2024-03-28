package com.spring.mvc.chap05.dto.response;


import com.spring.mvc.chap05.entity.Board;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter @ToString // 일방적으로 보여주기만 할 것이기 때문에 @Getter만 작성한다.
@EqualsAndHashCode
public class BoardDetailResponseDTO {
    private final int boardNo;
    private final String title;
    private final String content;
    private final String regDate;
    private final String writer;

    public BoardDetailResponseDTO(Board board) {
        this.boardNo = board.getBoardNo();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.regDate = BoardListResponseDTO.makePrettierDateString(board.getRegDate());
        this.writer = board.getWriter();
    }
}
