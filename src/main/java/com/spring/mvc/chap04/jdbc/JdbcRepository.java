package com.spring.mvc.chap04.jdbc;

import com.spring.mvc.chap04.entity.Person;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class JdbcRepository {
    // db 연결 설정 정보 (한 번 세팅해 놓으면 두고두고 쓰기 때문에 외우지 말기.)
    private String url = "jdbc:mysql://localhost:3306/spring_db?serverTimezone=Asia/Seoul"; // DB url 주소
    private String username = "spring"; // 계정명
    private String password = "spring"; // 비밀번호

    // JDBC 드라이버 로딩
    // 자바 프로그램과 데이터베이스간에 객체가 드나들 길을 뚫어 놓는다고 생각하기
    public JdbcRepository() {
        // 객체가 생성될 때 자바 프로그램과 데이터베이스간에 객체가 드나들 길을 뚫기
        try {
            // "com.mysql.cj.jdbc.Driver": Driver 클래스 경로
            // Driver클래스가 위치한 경로를 참고하여 Class.forName() 메서드로
            // 해당 Driver 클래스를 강제로 구동시키기(강제로 깨우기)
            // 강제로 구동시켜서 자바프로그램과 데이터베이스간에 객체가 드나들 수 있도록 "연결만" 시켜놓는 것이다.
            // 길을 뚫어놓는 것이다.
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    // 데이터베이스 커넥션 얻기(Connection 객체 얻기 -> 데이터베이스 접속을 담당하는 객체
    public Connection getConnection() throws SQLException {
        // 길을 뚫었다면,
        // 자바프로그램과 데이터베이스간에 객체가 드나들 수 있는 공간을 만들었다면,
        // 그후에 DriverManager클래스를 사용해 "연결 객체"를 얻는다.
        // ** 위에 준비한 url, username, password를 전달해서 DB 접속을 담당하는 Connection 받아오기.
        return DriverManager.getConnection(url, username, password);
    }

    // INSERT 기능 -> 테스트 클래스에서 테스트 하기
    public void save(Person person) {
        Connection conn = null;



        // 1. DB 연결하고,
        // 연결 정보를 얻어와야 한다.
        try {
            conn = getConnection();
            // 2. 트랜잭션을 시작
            conn.setAutoCommit(false); // 자동 커밋 비활성화.
            // 3. 실행할 SQL을 완성하기 (문자열)
            String sql = "INSERT INTO person (person_name, person_age) " +
                    //1) "VALUES ("+person.getPersonName()+", "+person.getPersonAge()+")";

                    //2) 들어갈 값이 위치할 곳에 ?를 채워줍니다.
                    "VALUES (?, ?)";

                    // 4. SQL을 실행시켜주는 객체를 얻어옵니다.
                    // prepareStatement의 매개값으로 미완성된  sql을 전달
                    PreparedStatement pstmt = conn.prepareStatement(sql);

                    // 5. ? 채우기(순서, 값의 타입을 고려해서 채우기)
                    // ? 채울 때 지목하는 인덱스는 1번부터
                    pstmt.setString(1, person.getPersonName());
                    pstmt.setInt(2, person.getPersonAge());

                    // 6. SQL 실행 명령
                    // INSERT, UPDATE, DELETE - executeUpdate();
                    // SELECT - executeQuery();
                    // 리턴 값은 성공한 쿼리문의 갯수, 성공하면 1이 리턴, 실패하면 0이 리턴
                    int result = pstmt.executeUpdate();

                    // 7. 트랜잭션 처리
                    if(result == 1) conn.commit();
                    else conn.rollback();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // 8. 접속 해제
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
