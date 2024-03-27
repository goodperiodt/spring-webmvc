package com.spring.mvc.chap04.dto;

import com.spring.mvc.chap04.entity.Grade;
import com.spring.mvc.chap04.entity.Score;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@RequiredArgsConstructor // final 필드 초기화하는 생성자
@Getter @EqualsAndHashCode @ToString // setter는 구현하지 않았다.
// -> 응답용이기 때문에 더 이상의 데이터의 변경을 막기 위해서다.
public class ScoreResponseDTO {

    private final int stuNum;
    private final String maskingName;
    private final double average;
    private final Grade grade;

    public ScoreResponseDTO(Score score) {
        this.stuNum = score.getStuNum();
        this.maskingName = makeMaskingName(score.getName());
        this.average = score.getAverage();
        this.grade = score.getGrade();
    }

    private String makeMaskingName(String name) {
        // 학생의 성만 빼고 나머지 이름을 *로 가려주는 기능
        String maskedName = String.valueOf(name.charAt(0)); // 성만 추출

        // 성 빼고 나머지 이름의 길이만큼 반복해서 *을 붙이겠다.
        for (int i = 0; i < name.length() -1; i++) {
            maskedName += "*";
        }

        return maskedName;
    }
}