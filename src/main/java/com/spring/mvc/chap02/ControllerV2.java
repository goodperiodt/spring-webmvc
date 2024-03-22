package com.spring.mvc.chap02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/model") // 공통 url 맵핑 -> 메서드마다 /model을 작성할 필요 없음.
public class ControllerV2 {
        /*
        /model/hobbies : GET
        -> hobbies.jsp파일로 사용자 이름정보와 취미목록을 뿌려주고 싶다.
        == 1. Model 객체 사용
        -> 자바가 갖고 있는 데이터를 JSP로 넘겨줄 때 사용하는 바구니같은 역할
     */
    @GetMapping("/hobbies")
    public String hobbies(Model model) { // 디스패쳐 서블릿아 Model객체를 보내줘
        System.out.println("/model/hobbies: GET 요청!");

        String name = "짹짹이";
        List<String> hobbyList = new ArrayList<>();
        Collections.addAll(hobbyList, "전깃줄 앉아있기", "좁쌀 훔쳐먹기", "짹짹거리기", "멍때리기");

        // Request Dispatcher에게 String name과 hobbyList를 반환 경로(chap02/hobbies)에 전달해달라고 하기, 디스패쳐 서블릿아 Model객체를 보내줘, Model에 name과 hobbyList를 담아서 반환 경로에 전달할 거거든.
        // Model 객체에 데이터 담기 ("이름", 값)
        model.addAttribute("userName", name);
        model.addAttribute("hobbies", hobbyList);

        return "chap02/hobbies";
    }
}
