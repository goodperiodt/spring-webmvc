package com.spring.mvc.chap05.dto.request;

import com.spring.mvc.chap05.entity.Reply;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode @NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyPostRequestDTO {
    /*
                const payload = {
                text: textVal,
                author: writerVal,
                bno: bno
            };
     */

    // NotNull: null만 안됨(빈 문자열은 됨)
    // NotBlank: null 안됨, 빈 문자열도 안됨
    @NotBlank // ReplyApiController 에서 @Validated를 붙여야 @NotBlank 와 @Size 사용 가능
    @Size(min = 1, max = 300)
    private String text; // 댓글 내용

    @NotBlank
    @Size(min =2, max = 8)
    private String author; // 댓글 작성자

    @NotNull
    private int bno; // 원본 글번호

    // dto를 entity로 바꾸는 변환 메서드
    public Reply toEntity() {
        return Reply.builder()
                .replyText(this.text)
                .replyWriter(this.author)
                .boardNo(this.bno)
                .build();
    }
}
