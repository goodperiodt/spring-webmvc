package com.spring.mvc.chap04.jdbc;

import com.spring.mvc.chap04.entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcRepositoryTest {
    @Autowired
    JdbcRepository repository;

    @Test
    @DisplayName("Person 객체 정보를 데이터베이스에 정상 삽입한다.")
        // 테스트의 이름을 붙여주는 아노테이션, 이 saveTest를 만든 목적을 제목으로 지정한다.
    void saveTest() {
        Person p = new Person(2, "박성희", 80);

        repository.save(p);
    }
}