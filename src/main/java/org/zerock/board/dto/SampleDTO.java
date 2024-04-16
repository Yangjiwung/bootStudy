package org.zerock.board.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data // 게터, 세터 , toStirng 등...
@Builder(toBuilder = true) // 빌더패턴
public class SampleDTO {
    // dto는 프론트에서 자바까지 객체를 담당한다.
    // entity는 db에서 자바까지 영속성을 담당한다.
    // 나중에는 dtotoentity 와 entitytodto라는 메서드가 이 2개를 전이역할 진행함

    private Long sno;
    private String first;
    private String last;
    private LocalDateTime regTime;

}
