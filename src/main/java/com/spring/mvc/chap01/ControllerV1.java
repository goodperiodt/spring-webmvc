package com.spring.mvc.chap01;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// 컨트롤러: 클라이언트의 요청을 받아서 처리 후 응답을 결정하는 역할
@Controller // 빈 등록 아노테이션: 이 클래스의 객체 생성 및 관리는 스프링 컨테이너가 처리한다.
public class ControllerV1 {

    /*
        @RequestMapping("/")
        public String home() {
            return "index";
        }
     */

    // 스프링에서는 메서드별로 url을 매핑하여 처리한다.
    // @RequestMapping(value = "/food", method = RequestMethod.GET) // /food라는 경로(요청)가 들어오면 food() 메서드를 실행한다.
    @GetMapping("/food")
    public String food() {
        // 리턴문에는 어떤 jsp로 포워딩할지 경로를 적는다.
        // 경로를 작성할 때는 view resolver 세팅에 작성된 접두어, 접미어를 고려하여 씁니다.
        // viewResolver에 /WEB-INF/view/~~~~~~.jsp가 작성되어 있다.
        return "chap01/food";
    }


    //====================== 요청 파라미터 읽기(클라이언트가 보낸 데이터) =====================
    // 1. HttpServletRequest 객체 이용하기 -> 전통적 방식
    // ** foodReg() 메서드는 디스패쳐 서블릿이 호출하는 것이다.

    // @RequestMapping(value ="/food", method = RequestMethod.POST)
    /*
    @PostMapping("/food")
    public String foodReg(HttpServletRequest request) {
        String foodName = request.getParameter("foodName");
        String category= request.getParameter("category");

        System.out.println("foodName = " + foodName);
        System.out.println("category = " + category);

        return null;
    }
    */
    // 2. @RequestParam 이용하기
//    @PostMapping("/food")
//    public String food(@RequestParam("foodName") String name
//                       // 파라미터명이랑 변수명이랑 같을 경우 @RequestParam("category") 생략 가능
//                       // @Request() 아노테이션 생략 가능
//                      //,@RequestParam("category") String category
//                       ,String category
//                      //,@RequestParam("price") int price
//                       ,int price) {
//        System.out.println("name = " + name);
//        System.out.println("category = " + category);
//        System.out.println("price = " + price);
//        return null;
//    }

    // 3. DTO(Data Transfer Object: 읽을 때 디티오) 객체 사용 -> 커맨드 객체를 활용한 파라미터 처리
    // 처리해야 할 파라미터 양이 많거나, 서로 연관되어 있는 데이터인 경우 사용.
    
    @PostMapping("/food")
    public String food(FoodOrderDTO orderDTO) {
        System.out.println("orderDTO = " + orderDTO);
        return null;
    }


}
