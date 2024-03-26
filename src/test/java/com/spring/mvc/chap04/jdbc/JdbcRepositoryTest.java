package com.spring.mvc.chap04.jdbc;

import com.spring.mvc.chap04.entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Test
    @DisplayName("회원번호가 2번인 회원의 이름과 나이를 수정해야 한다.")
    void upDateTest() {
        // given: 테스트를 위해 데이터를 세팅
        int id = 2;
        String newName = "개굴이";
        int newAge = 15;
        Person p = new Person(id, newName, newAge);

        // when: 테스트 목표를 확인하여 실제 테스트가 진행되는 구간
        repository.update(p);

        // then: 테스트 결과를 확인하는 구간(단언 기법 -> Assertion)
        // 단언: 테스트의 결과는 모호하면 안된다.


    }

    @Test
    @DisplayName("회원번호가 1인 회원을 삭제해야 한다.")
    void deleteTest() {
        int id =1;
        repository.delete(id);
    }

    @Test
    @DisplayName("더미 데이터 10개를 삽입해야 한다.")
    void vulkInsert() {
        for (int i = 0; i < 10; i++) {
            Person p = new Person(0, "랄라라"+i, i+10);
            repository.save(p);
        }
    }

    @Test
    @DisplayName("전체 회원을 조회하면 회원 리스트의 사이즈는 10일 것이다.")
    void findAllTest() {
        List<Person> people = repository.findAll();

        for (Person person : people) {
            System.out.println(person);
        }

        // 나는 people.size()가 10과 같은 것임을 단언하겠다!
        assertEquals(10, people.size());
        assertNotNull(people);
    }

    @Test
    @DisplayName("특정 아이디 회원을 조회하면 하나의 회원만 조회될 것이고," +
    "없는 아이디를 조회하면 null이 리턴될 것이다.")
    void findOne() {
        int id = 600;

        Person p = repository.findOne(id);
        System.out.println(p);

        assertNull(p);

    }
}