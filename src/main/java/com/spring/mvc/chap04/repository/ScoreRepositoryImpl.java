package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Grade;
import com.spring.mvc.chap04.entity.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository // Bean으로 등록해놔야 Service에 주입이 가능하니까
@RequiredArgsConstructor
public class ScoreRepositoryImpl implements ScoreRepository {

    // 클래스(ScoreRepositoryImpl) 안의 클래스(ScoreMapper): 이너클래스: 중첩 클래스

    // ** 내부(중첩) 클래스 (inner class)
    // 두 클래스가 굉장히 긴밀한 관계가 있을 때 주로 선언.
    // 해당 클래스 안에서만 사용할 클래스를 굳이 새 파일로 선언하지 않고 만들 수 있습니다.
    // * 내부 클래스는 public을 붙이지 않는다.

    class ScoreMapper implements RowMapper<Score> {
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

    // Spring-jdbc의 핵심 객체인 JdbcTemplate의 의존성 주입(생성자 주입)
    // 데이터베이스 접속 객체(Connection)를 바로 활용하는 것이 가능 -> 미리 다 셋팅해놓음
    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(Score score) {

        String sql = "INSERT INTO tbl_score" +
                "(stu_name, kor, eng, math, total, average, grade) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        int result = jdbcTemplate.update(sql, score.getName(), score.getKor(), score.getEng()
                , score.getMath(), score.getTotal(), score.getAverage(), score.getGrade().toString());

        return (result == 1) ? true:false;
    }

    @Override
    public List<Score> findAll() {
        String sql = "SELECT * FROM tbl_score";
        List<Score> scoreList = jdbcTemplate.query(sql, new ScoreMapper());
        return scoreList;
        // return jdbcTemplate.query(sql, new ScoreMapper);
    }

    @Override
    public boolean delete(int stuNum) {
        String sql = "DELETE FROM tbl_score WHERE stu_num = ?";
        return jdbcTemplate.update(sql, stuNum) == 1;
    }

    @Override
    public Score findOne(int stuNum) {
        String sql = "SELECT * FROM tbl_score WHERE stu_num = ?";
        Score score = null;

        try {
            // queryForObject는 조회 결과가 없을 시 예외가 발생한다.
            score = jdbcTemplate.queryForObject(sql, new ScoreMapper(), stuNum);
        } catch (DataAccessException e) {
            // 조회 결과 없다면 catch문이 실행 -> null을 리턴.
            return null;
        }
        return score;
        // return jdbcTemplate.queryForObject(sql, new ScoreMapper(), stuNum);
    }


}
