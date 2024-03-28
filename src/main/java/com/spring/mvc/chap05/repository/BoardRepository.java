package com.spring.mvc.chap05.repository;


import com.spring.mvc.chap05.entity.Board;

import java.util.List;

// 게시판 CRUD 기능 명세
public interface BoardRepository {
    // 목록 조회
     List<Board> findAll(); // sql을 생각하면 매개변수의 존재 여부를 알 수 있고, 반환타입은 리스트



    // 상세 조회
    Board findOne(int boardNo);

    // 게시물 등록
    void save(Board board);

    // 게시물 삭제
    void delete(int boardNo);

    // 조회수 처리
    void updateViewCount(int bno);
}
