package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tbl_memo") // db테이블 명을 지정
@ToString // 객체가 아닌 문자로
@Getter
@Builder // 메서드.필드(값).필드값(값).bulider;(빌더패턴)
// @AllArgsConstructor @NoArgsConstructor 두가지 필수
// @AllArgsConstructor ( new 클래스(모든 필드값 파라미터로 만듦) )
// @NoArgsConstructor ( new 클래스(); )
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
    // 엔티티는 데이터베이스에 테이블과 필드를 생성시켜 관리하는 객체
    // 엔티티를 이용해서 JPA를 활성화 하려면 application.properties에 필수 항목 추가
    @Id // 기본키를 명시(PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false) // 200자, not null
    private String memoText;


}
