package com.spring.mvc.chap03;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/hw")
@Controller
public class LoginController {
    /*
    1번요청: 로그인 폼 화면 열어주기
    - 요청 URL : /hw/s-login-form : GET
    - 포워딩 jsp파일 경로:  /WEB-INF/views/chap03/s-form.jsp
    - html form: 아이디랑 비번을 입력받으세요.
     */
    @GetMapping("/s-login-form")
    public String loginView() {
        return "/chap03/s-form";
    }

    /*
        2번요청: 로그인 검증하기
        - 로그인 검증: 아이디를 grape111이라고 쓰고 비번을 ggg9999 라고 쓰면 성공
        - 요청 URL : /hw/s-login-check : POST
        - 포워딩 jsp파일 경로:  /WEB-INF/views/chap03/s-result.jsp
        - jsp에게 전달할 데이터: 로그인 성공여부, 아이디 없는경우, 비번 틀린경우

     */
    @PostMapping("/s-login-check")
    public String loginCheck(String id,
                             String pw,
                             Model model) {
//        String cId = "grape111";
//        String cPw = "ggg9999";
        String msg = "";

//        if (!cId.equals(id)) {
//            msg = "아이디를 다시 확인해주세요!";
//        } else if(cId.equals(id)){
//            if (!cPw.equals(pw)) {
//                msg = "비밀번호를 다시 확인해주세요!";
//            } else {
//                msg = "로그인이 성공했습니다!";
//            }
//
//            return msg;
//        }

        if(id.equals("grape111")) {
            if(pw.equals("ggg9999")) {
                // 로그인 성공
                msg = "로그인 성공";
            } else {
                // 패스워드 틀림
                msg ="비밀번호가 틀렸습니다.";
            }
        } else {
            // 아이디가 일치하지 않음
            msg = "아이디가 일치하지 않습니다.";
        }


        model.addAttribute("msg", msg);
        return "/chap03/s-result";
    }
}
