package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


// 서비스가 BoardRepositoryIm 객체를 필요로 하기 때문에 @Repository 를 작성함으로써
// 빈 등록을 진행한다. 레파지토리단은 DB에 직접 접근하는 객체다.
@RequiredArgsConstructor // 생성자 이용해서 초기화 진행
@Repository
public class BoardRepositoryIm implements BoardRepository {

    class BoardMapper implements RowMapper<Board> {
        // RowMapper<>제네릭 안에 리턴타입(Board)을 작성해야
        // 하기 오버라이드 생성시 리턴타입이 Board로 작성된다.
        @Override
        public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
            Board board = new Board(
                    // 각각의 컬럼명을 매개변수에 작성하여
                    // 컬럼명에 해당하는 데이터를 가져와
                    // 생성자를 활용해 객체를 생성한다.
                    rs.getInt("board_no"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getInt("view_count"),
                    rs.getTimestamp("reg_date").toLocalDateTime(), // 뭔말이야
                    rs.getString("writer")
            );

            return board;
        }
    }

    private final JdbcTemplate template;




    @Override
    public List<Board> findAll() {
        String sql = "SELECT * FROM tbl_board ORDER BY board_no DESC";
        // 여러 행이 반환될 것 같으면 query() 메서드를 사용하기
        // query(sql문, RowMapper를 구현한 클래스)
        List<Board> boardList = template.query(sql, new BoardMapper());
        return boardList;
    }

    @Override
    public Board findOne(int boardNo) {
        String sql = "SELECT * FROM tbl_board WHERE board_no = ?";
        Board board = null;

        try {
            board = template.queryForObject(sql, new BoardMapper(), boardNo); // boardNo는 ? 채울 값
        } catch (DataAccessException e) {
            return null;
        }
        return board;
    }

    @Override
    public void save(Board board) {
        // sql문 작성
        String sql = "INSERT INTO tbl_board (title, content, writer) " +
                "VALUES (?, ?, ?)";

        template.update(sql, board.getTitle(), board.getContent(), board.getWriter());
    }

    @Override
    public void delete(int boardNo) {
        String sql = "DELETE FROM tbl_board WHERE board_no = ?";
        template.update(sql, boardNo);
    }

    @Override
    public void updateViewCount(int bno) {
        String sql = "UPDATE tbl_board SET view_count = view_count + 1 " +
                "WHERE board_no = ?";
        template.update(sql, bno);
    }
}
