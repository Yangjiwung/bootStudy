package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") // writer변수는 toString 하지 않음
public class Board extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동번호 생성용 코드
    private Long bno;
    
    private String title;
    public void changeTitle(String title){
        this.title = title; // 수정처리를 위해 자체적으로 수정 가능 한 내용만 세터생성
    }
    
    private String content;
    public void changeContent(String content){
        this.content = content; // 수정처리를 위해 자체적으로 수정 가능 한 내용만 세터생성
    }
    
    @ManyToOne(fetch = FetchType.LAZY) // 데이터를 필요할때 불러 올수 있음 FetchType.EAGER는 전부를 불러옴 
    private Member writer; // 연관관계 지정 fk
}
