package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Length;

@Entity // 테이블을 자동으로 관리
@Getter
@Builder
@AllArgsConstructor // @Builder 필수
@NoArgsConstructor // @Builder 필수
@ToString // 객체가 아닌 문자열로 변환
public class Guestbook extends BaseEntity{
    // extends BaseEntity를 상속 받아 날짜 시간 자동 처리
    @Id // Guestbook테이블의 pk지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 마리아DB용으로 자동번호 생성
    private Long gno; // 방명록에서 사용할 번호

    @Column(length = 100, nullable = false) // 문자 100, not null
    private String title;   // 제목

    @Column(length = 1500, nullable = false) // 문자 1500, not null
    private String content; // 내용

    @Column(length = 50, nullable = false) // 문자 50, not null
    private String writer;  // 작성자


    public void changeTitle(String title){
        this.title = title; // 세터 역할 (수정할 때 사용)
    }

    public void changeContent(String content){
        this.content = content; // 세터 역할 (수정할 때 사용)
    }

}
