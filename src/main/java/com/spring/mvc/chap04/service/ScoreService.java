package com.spring.mvc.chap04.service;

import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Controller와 Repository 사이에 위치하여
// 중간 로직을 처리하는 Service
// Controller가 Service에게 일을 시키고, Service는 Repository에게 일을 시킨다.
//@Component 중에서도 Service 계층임을 알리는 @Service
@Service
@RequiredArgsConstructor
public class ScoreService {
    // 서비스는 레파지토리 계층과 의존관계가 있으므로 객체가 생성될 때 자동 주입 세팅
    private final ScoreRepository repository;
    // 성적 입력 중간 처리
    // INSERT, UPDATE, DELETE 수행시 성공적으로 수행되면 1을 리턴하고,
    // 실패하면 0을 반환한다.
    // 디스패쳐 서블릿이 해당 데이터를 감싸서 (컨트롤러에서) 서비스단으로 전달한다.

    // 컨트롤러가 dto를 넘김 -> 값을 정제하고 entity로 변환 -> 레파지토리 계층에게 넘기자.
    //
    public boolean insertScore(ScoreRequestDTO dto) {
        Score score = new Score(dto);
        return repository.save(score);
    }
}
