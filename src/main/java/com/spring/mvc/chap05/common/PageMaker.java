package com.spring.mvc.chap05.common;


public class PageMaker {


    // 페이지 시작번호와 끝번호(1~10, 11~20)
    private int begin, end, finalPage; // finalPage는 보정된 끝 페이지 번호를 의미한다.

    // 이전, 다음 버튼 활성화 여부
    private boolean prev, next;


    // 현재 사용자가 요청한 페이지 정보
    private Page page;

    // 총 게시물 수
    private int totalCount;

    // 외부로부터 전달받을 데이터는 Page와 totalCount밖에 없다.

    public PageMaker(Page page, int totalCount) {
        this.page = page;
        this.totalCount = totalCount;
    }

    // 한 화면에 보여질 페이지 버튼 갯수
    private static final int PAGE_COUNT = 10;
    private void makePageInfo() {

    }
}
