package com.spring.mvc.chap04.jdbc;

import com.spring.mvc.chap04.entity.Person;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


    // UPDATE 기능
    public void update(Person person) {
        // 1. 데이터베이스 연결부터 -> Connection getConnection()
        Connection conn = null;

        try {
            // url, id, pw를 가지고 DB에 접속
            conn = getConnection();
            // 트랜잭션(오토커밋 끄기)
            conn.setAutoCommit(false);

            // 실행할 sql 미리 준비
            String sql = "UPDATE person SET person_name = ?, person_age = ? WHERE id = ?";

            // sql문을 실행하는 객체 얻기
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, person.getPersonName());
            pstmt.setInt(2, person.getPersonAge());
            pstmt.setInt(3, person.getId());

            //sql 실행 명령!
            int result = pstmt.executeUpdate();

            if(result == 1) conn.commit();
            else conn.rollback();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    // Delete 기능
    public void delete(int id) {
        Connection conn = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            //String sql = "DELETE FROM person WHERE id = "+id;
            String sql = "DELETE FROM person WHERE id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id); // ?로 세팅하지 않을 경우 바로 이 코드는 작성하지 않는다.

            int result = pstmt.executeUpdate();

            if(result == 1) conn.commit();
            else conn.rollback();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 전체회원 조회
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();

        Connection conn = null;

        try {
            conn = getConnection();

            String sql = "SELECT * FROM person";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // SELECT문 실행은 은 executeQuery();
            // ResultSet: SELECT 조회 결과를 들고 있는 객체 -> 조회 내용을 자바 타입으로 변환할 수 있는 기능을 제공한다.
            ResultSet rs =pstmt.executeQuery();


            // rs.next() 메서드는 조회 대상이 존재한다면 true를 리턴하면서 한 행을 타겟으로 잡는다.
            // 더 이상 조회할 데이터가 없다면 false를 리턴한다.
            // 만약 내가 작성한 sql문의 조회 결과가 여러 행이라면 반복문을 이용해서 한 행씩 데이터를 가져온다.
            // 그리고 더 이상 조회할 데이터가 없다면 반복문이 종료되도록 설계한다.
            while(rs.next()) {
                // while문이 실행됐다는 것은 특정 한 행이 지목된 상태다.
                // 행의 컬럼을 지목해서 데이터를 가져온다.(타입 확인 필요)
                int id = rs.getInt("id");
                String personName = rs.getString("person_name");
                int personAge = rs.getInt("person_age");

                Person p = new Person(id, personName, personAge);

                people.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return people;
    }
    
    // 단일 조회(pk로 조회)
    public Person findOne(int id) {
        Connection conn = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM person WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            // 가져올 데이터가 여러 행이면 반복문으로 처리
            // 지금은 pk로 조회했기 때문에 행이 하나이거나, 없을 수 있다 -> if문으로 처리
            if(rs.next()) {
                int personId = rs.getInt("id");
                String personName = rs.getString("person_name");
                int personAge = rs.getInt("person_age");

                return new Person(personId, personName, personAge);
            }

        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 조회 결과가 없음을 의미
        return null;
    }
}
