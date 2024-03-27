package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Grade;
import com.spring.mvc.chap04.entity.Score;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// JdbcTemplate에서 SELECT 쿼리를 위한 ResultSet 사용을 편하게 하기 위한 클래스를 생성.
// JdbcTemplate한테 조회한 내용을 어떻게 가공해야 하는지를 알려주는 클래스
// RowMapper 인터페이스를 구현해야 한다.
public class ScoreMapper implements RowMapper<Score> {
    @Override
    // 조회결과를 들고 있는 ResultSet rs
    public Score mapRow(ResultSet rs, int rowNum) throws SQLException {
        Score score = new Score(
                rs.getString("stu_name"),
                rs.getInt("kor"),
                rs.getInt("eng"),
                rs.getInt("math"),
                rs.getInt("stu_num"),
                rs.getInt("total"),
                rs.getDouble("average"),
                Grade.valueOf(rs.getString("grade")) // -> 이 코드 무슨말인지 모르겠음
        );


        return score;
    }
}
