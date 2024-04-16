package org.zerock.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page; // 페이지
    private int size; // 페이지당 갯수

    private String type;

    private String keyword;

    public PageRequestDTO(){
        this.page = 1; // 기본값 1페이지
        this.size = 10; // 10개
    }

    public Pageable getPageable(Sort sort){

        return PageRequest.of(page -1, size, sort); // page = 1 - 1 = 0
        // jpa를 이용하는 경우 기본페이지가 0부터 시작하때문에 1페이지부터 시작 하려면 page - 1 을함
        // 정렬은 나중에 다양한 상황에 사용하기 위해 별도 파라미터로 받음
    }
}
