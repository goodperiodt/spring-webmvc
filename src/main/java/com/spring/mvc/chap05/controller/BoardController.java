package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.dto.request.BoardWriteRequestDTO;
import com.spring.mvc.chap05.dto.response.BoardListResponseDTO;
import com.spring.mvc.chap05.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // 빈 등록, 요청이 들어왔을 때 디스패쳐가 요청을 처리할 컨트롤러를 찾을 때, 이 컨트롤러 클래스를 찾을 수 있다.
@RequiredArgsConstructor
// @RequestMapping() 메서드를 사용하여 (요청) url의 공통점을 (String 타입의) 매개변수로 전달한다.
@RequestMapping("/board")
public class BoardController {
    private final BoardService service;



    // 1. 목록 조회 요청 (/board/list : GET)
    // chap05/list.jsp 에 조회한 목록 뿌리기
    // 일단 Entity로 전달?

    /*
    @GetMapping("/list")
    public String viewList() {
        System.out.println("/board/list: GET");


        return "chap05/list";
    }
     */



    // 2. 글쓰기 화면요청 (/board/write : GET)
    // chap05/write.jsp로 이동

    // 브라우저 주소창에 localhost:8181/board/write 요청이 들어오면(입력되면)
    // /WEB-INF/views/chap05/write.jsp 파일을 보여주는 메서드다.

    /*
    리졸버 매핑
    spring.mvc.view.prefix=/WEB-INF/views/
    spring.mvc.view.suffix=.jsp
     */

    @GetMapping("/write")
    public String start() {
        System.out.println("/board/write:GET!!");
        return "chap05/write";
    }

    // 3. 글쓰기 등록요청 (/board/write : POST)
    // BoardWriteRequestDTO를 활용하여 파라미터를 처리한다.
    // -> BoardWriteRequestDTO 클래스를 dto.request 패키지에 생성한다.
    // 등록 완료 후에는 목록 조회 요청이 다시 들어오게끔 처리한다. redirect
    @PostMapping("/write")
    public String write(BoardWriteRequestDTO dto) {
        System.out.println("/board/write: POST");
        System.out.println("rDTO = " + dto);

        service.register(dto);

        return "redirect:board/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<BoardListResponseDTO> dtoList = service.getList();
        model.addAttribute("bList", dtoList);
        return "chap05/list";
    }






    // 4. 글 삭제 요청 (/board/delete : GET)
    // 글번호 전달되면 삭제 진행






    // 5. 글 상세보기 요청 (/board/detail : GET)
    // 글번호 전달되면 해당 내용 상세보기 처리
    // chap05/detail.jsp 로 이동




}
