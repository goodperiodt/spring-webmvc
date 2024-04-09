package com.spring.mvc.chap05.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class SnsLoginService {
    // 카카오 로그인 처리
    public void kakaoLogin(Map<String, String> params) {
        // 인가 코드를 가지고 토큰을 발급해야 한다.
        // 토큰 발급 요청 후 작업해야할 게 있다.
        getKakaoAccessToken(params);
    }

    // 토큰 발급 요청
    private void getKakaoAccessToken(Map<String, String> requestParams) {
        String requestUri = "https://kauth.kakao.com/oauth/token";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        // 요청 바디에 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", requestParams.get("appKey"));
        params.add("redirect_uri", requestParams.get("redirect"));
        params.add("code", requestParams.get("code"));

        // 카카오 인증 서버로 POST 요청 날리기
        // 백엔드에서 RestTemplate을 사용하면, 다른 서버로 비동기 요청(처리)을 진행할 수 있다. 자바스크립트에서는 fetch API를 사용했었다.

        RestTemplate template = new RestTemplate();

        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers); // 헤더 정보와 파라미터를 하나로 묶기.

        ResponseEntity<Map> responseEntity = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);

        /*
            - RestTemplate객체가 REST API 통신을 위한 API인데 (자바스크립트 fetch역할)
            - 서버에 통신을 보내면서 응답을 받을 수 있는 메서드가 exchange
            param1: 요청 URL
            param2: 요청 방식 (get, post, put, patch, delete...)
            param3: 요청 헤더와 요청 바디 정보 - HttpEntity로 포장해서 줘야 함
            param4: 응답결과(JSON)를 어떤 타입으로 받아낼 것인지 (ex: DTO로 받을건지 Map으로 받을건지)
         */

        // 응답 데이터에서 JSON 추출
        Map<String, Object> responseJSON = (Map<String, Object>)requestEntity.getBody();

        // access token 추출 (카카오 로그인 중인 사용자의 정보를 요청할 때 필요한 토큰)
        String accessToken = (String) responseJSON.get("access_token");

        return accessToken;

    }
}
