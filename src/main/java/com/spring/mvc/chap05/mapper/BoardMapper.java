package com.spring.mvc.chap05.mapper;

import com.spring.mvc.chap05.common.Page;
import com.spring.mvc.chap05.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



// 마이바티스의 SQL 실행을 위한 인터페이스임을 명시
// 마이바티스는 인터페이스를 구현하는 클래스를 만들지 않는다
// .xml 파일 내부에 인터페이스를 구현하는 설정을 작성한다.
@Mapper
public interface BoardMapper {
    // 목록 조회
    List<Board> findAll(Page page); // sql을 생각하면 매개변수의 존재 여부를 알 수 있고, 반환타입은 리스트



    // 상세 조회
    Board findOne(int boardNo);

    // 게시물 등록
    void save(Board board);

    // 게시물 삭제
    void delete(int boardNo);

    // 조회수 처리
    void updateViewCount(int bno);




}
