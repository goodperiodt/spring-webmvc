package com.spring.mvc.chap05.dto.response;


import com.spring.mvc.chap05.entity.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter @ToString
@EqualsAndHashCode
// 목록쪽에 응답하기 위한 DTO
public class BoardListResponseDTO {
    private final int boardNo;
    private final String shortTitle; // 5글자 이상이면 잘라내기
    private final String shortContent; // 30자 이상이면 잘라내기\
    private final String regDate; // 날짜 패턴 yyyy-MM-dd HH:mm
    
    private final int viewCount;
    private final String writer;

    public BoardListResponseDTO(Board board) {
        this.boardNo = board.getBoardNo();
        this.shortTitle = makeShortTitle(board.getTitle());
        this.shortContent = makeShortContent(board.getContent());
        this.viewCount = board.getViewCount();
        this.regDate = makePrettierDateString(board.getRegDate()); // 코드 정제를 IT쪽에서는 prettier라고 한다.
        this.writer = board.getWriter();
    }

    public static String makePrettierDateString(LocalDateTime regDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dtf.format(regDate);
    }


    /**
     *
     * @param targetString : 줄이고 싶은 원본 문자열
     * @param wishLength : 짜르고 싶은 글자 수
     * @return : wishLength보다 targetString이 길면
     *              wishLength만큼 짤라서 뒤에 ... 붙여서 리턴
     */
    private static String sliceString(String targetString, int wishLength) {
        return (targetString.length() > wishLength)
                ? targetString.substring(0, wishLength) + "..."
                : targetString
                ;
    }

    private String makeShortTitle(String title) {
        return sliceString(title, 5);
    }

    private String makeShortContent(String content) {
        return sliceString(content, 30);
    }
}
