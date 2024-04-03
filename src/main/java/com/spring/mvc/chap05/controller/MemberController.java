package com.spring.mvc.chap05.controller;

import com.mysql.cj.log.Log;
import com.spring.mvc.chap05.dto.request.LoginRequestDTO;
import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.service.LoginResult;
import com.spring.mvc.chap05.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 가입 양식 화면 요청
    // 응답하고자 하는 화면의 경로가 url과 동일하다면 void로 처리할 수 있습니다. (선택사항)
    @GetMapping("/sign-up")
    public void signUp() {
        System.out.println("/members/sign-up: GET");
    }

    // 아이디, 이메일 중복체크 비동기 요청 처리
    @GetMapping("/check/{type}/{keyword}")
    @ResponseBody
    public ResponseEntity<?> check(@PathVariable String type,
                                   @PathVariable String keyword) {
        System.out.println("/members/check: async GET");
        System.out.println("type = " + type);
        System.out.println("keyword = " + keyword);

        boolean flag = memberService.checkDuplicateValue(type, keyword);

        return ResponseEntity.ok().body(flag);
    }

    @PostMapping("/sign-up")
    public String signUp(SignUpRequestDTO dto) {
        System.out.println("/members/sign-up: POST!");
        System.out.println("dto = " + dto);

        memberService.join(dto);
        return "redirect:/board/list";
    }

    // 로그인 양식 화면 요청 처리
    @GetMapping("/sign-in")
    public void signIn() {
        System.out.println("/members/sign-in: GET!");
    }

    // 로그인 검증 요청
    @PostMapping("/sign-in")
    public String signIn(LoginRequestDTO dto,
                         // Model에 담긴 데이터는 리다이렉트 시 jsp로 전달되지 못한다.
                         // 리다이렉트는 응답이 나갔다가 재요청이 들어오는데, 그 과정에서
                         // 첫번째 응답이 나가는 순간 모델은 소멸함. (Model의 생명주기는 한 번의 요청과 응답 사이에서만 유효)
                         RedirectAttributes ra
                         ) {
        System.out.println("/members/sign-in: POST!");
        System.out.println("dto = " + dto);

        LoginResult result = memberService.authenticate(dto);
        System.out.println("result = " + result);

        ra.addFlashAttribute("result", result);

        if (result == LoginResult.SUCCESS) { // 로그인 성공 시
            return "redirect:/board/list";
        }

        return "redirect:/members/sign-in"; // 로그인 실패 시

    }



}















