package com.spring.mvc.chap04.Controller;

/*
    # 컨트롤러
    - 클라이언트의 요청을 받아서 처리하고 응답을 제공하는 객체
    # 요청 URL Endpoint
    1. 학생의 성적정보 등록폼 화면을 보여주고
       동시에 지금까지 저장되어 있는 성적 정보 목록을 조회
    - /score/list   :   GET
    2. 학생의 입력된 성적정보를 데이터베이스에 저장하는 요청
    - /score/register   :  POST
    3. 성적정보를 삭제 요청
    - /score/remove    :  GET or POST
    4. 성적정보 상세 조회 요청
    - /score/detail  :   GET
 */

import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/score")
@RequiredArgsConstructor // final이 붙은 필드를 초기화하는 생성자를 선언
public class ScoreController {

    // final 값은 생성과 동시에 초기화를 해야하고,
    // @RequiredArgsConstructor가 final이 붙은 필드를
    // 초기화하는 생성자를 선언해주는 기능이 있다.
    // ** DB에 데이터를 저장하기 위해 컨트롤러는 서비스가 꼭 필요하다(의존관계)
    // 의존 객체는 불변성을 띠게 작성하는 것이 좋다. -> 롬복기능중 RequiredArgsConstructor로 초기화 진행
    private final ScoreService service;

    // ScoreController 객체가 생성 될 때,
    // 서비스 객체가 자동 생성된다.
    /* - FM방식으로 처리 -> 롬복으로 대체. 생성자가 단 하나라면 autowired를 생략할 수 있음.
    @Autowired
    public ScoreController(ScoreService service) {
        this.service = service;
    }
     */

    // 1. 성적 입력폼 띄우기
    @GetMapping("/list")
    public String list() {
        return "chap04/score-list";
    }

    // 2. 성적정보를 데이터 베이스에 저장하는 요청
    @PostMapping("/register")
    public String register(ScoreRequestDTO dto) {
        System.out.println("/score/register: POST!!!");
        System.out.println("dto = " + dto);

        service.insertScore(dto);

        // 등록이 완료됐다면, 목록 화면으로 데이터를 전달해서 목록 화면을 보여주고 싶다.

        /*
            # forward vs redirect
            - 포워드는 요청 리소스를 그대로 전달해줌.
            - 따라서 URL이 변경되지 않고 한번의요청과 한번의 응답만 이뤄짐
            - forward할 때는 포워딩할 파일의 경로를 적습니다. (/views/chap04/score-list.jsp)

            - 리다이렉트는 요청후에 자동응답이 나가고
              2번째 자동요청이 들어오면서 2번째 응답을 내보냄
            - 따라서 2번째 요청의 URL로 자동 변경됨
            - redirect 할 때는 다시 들어왔으면 하는 요청 url을 적는 것(/score/list -> 목록 요청)
         */

        // redirect는 내가 요청한 url로 클라이언트측의 요청이 바로 들어온다.
        return "redirect:/score/list";
    }

    // 성적 목록 조회
    @GetMapping("/list")
    public String list(Model model) {
        System.out.println("/score/list: GET!!!");

        List<Score> scoreList = service.findAll();
        model.addAttribute("sList", scoreList);
        return "chap04/score-list";
    }


}
