package com.spring.mvc.chap05.mapper;

import com.spring.mvc.chap05.common.Page;
import com.spring.mvc.chap05.entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {

    // 댓글 등록
    void save(Reply reply);

    // 댓글 수정 (내용)
    void modify(Reply reply);

    // 댓글 삭제
    void delete(int replyNo);

    // 댓글 개별 조회
    Reply findOne(int replyNo);

    // 페이징을 적용한 댓글 전체 목록 조회
    // MyBatis에서 mapper에 매개변수가 2개 이상일 때는 변수명으로 바로 지목할 수가 없다.
    // 1. 변수를 하나만 쓰자 (전달하고자 하는 값이 여러 개라면 객체로 한번에 포장해서 넘기자)
    // 2. Map으로 포장해서 넘기자. (Map의 key가 xml에서 지목하는 이름이 된다.)
    // 3. @Param을 사용해서 직접 이름 붙이기
    //      -> xml에서 boardNo를 지목하고 싶으면 bn을 page객체를 지목하고 싶으면 p를 사용한다.
    List<Reply> findAll(@Param("bn") int boardNo, @Param("p") Page page);

    // 댓글 총 개수 조회
    int count(int boardNo);

}













