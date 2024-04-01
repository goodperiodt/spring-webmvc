package com.spring.mvc.chap05.api;

import com.spring.mvc.chap05.dto.request.ReplyPostRequestDTO;
import com.spring.mvc.chap05.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * REST API URL 설계 원칙
 * - CRUD는 URL에 명시하는게 아니라 HTTP method로만 표현해야 함!!
 * => /replies/write      (X)
 * => /replies   :  POST  (O)
 *
 * => /replies/all        (X)   - 전체조회
 * => /replies   :  GET   (O)   - 전체조회
 * => /replies/17  : GET        - 단일조회
 *
 * => /replies/delete?replyNo=3   (X)
 * => /replies/3    :   DELETE    (O)
 */

// url만으로 이 요청이 무엇을 대표하는지(GET인지, POST인지, DELETE인지 등)를 판단하는 REST API
// @RequestParam: 동기 요청에서 ?뒤에 붙은 파라미터
// @RequestBody: 비동기 요청에서 요청객체 바디안에 있는 JSON을 파싱    
@Controller
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;


    // 댓글 목록 조회 요청
    // URL: /api/v1/replies/글번호
    @GetMapping("/{boardNo}")
    public ResponseEntity<?> list(@PathVariable("boardNo") int boardNo) {
        System.out.println("/api/v1/replies/"+boardNo+": GET!");
        replyService.getList(boardNo);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> create(@Validated @RequestBody ReplyPostRequestDTO dto,
                         BindingResult result) { // BindingResult는 검증 결과 메시지를 가진 객체.

        // 입력 값 검증에 걸리면 400번 status와 함께 메시지를 클라이언트로 전송
        if(result.hasErrors()) {
            // ResponseEntity는 응답에 관련된 여러가지 정보 (상태코드, 전달할 데이터 등...) 를
            // 한 번에 객체로 포장해서 리턴할 수 있게 하는 Spring에서 제공하는 객체.
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }


        System.out.println("/api/vi/replies: POST");
        System.out.println("dto = " + dto);

        replyService.register(dto);

        return ResponseEntity.ok("success");
    }
}
