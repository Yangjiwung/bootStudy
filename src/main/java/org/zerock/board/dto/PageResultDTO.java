package org.zerock.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {


    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 목록 사이즈
    private int size;

    // 시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    // 이전, 다음
    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;


    public PageResultDTO(Page<EN> result, Function<EN, DTO>fn){ //  Function<EN, DTO>fn 객체들을 DTO로 변환 하는 기능
        // Page<EN> result : PageResultDTO Page<Entity>타입을 이용해 생성할 수 있도록 셋팅

        dtoList = result.stream().map(fn).collect(Collectors.toList()); // ??

        totalPage = result.getTotalPages(); //  dto의 총 페이지수를 가져옴

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1; // 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();

        // temp end page 가상의 마지막 페이지
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        start = tempEnd - 9 ; // 마지막 페이지 - 9  ex: 20 - 9 = 11

        prev = start > 1; // 이전 페이지는 현재 페이지 -1 다음페이지로 넘어가면 true를 반환

        // 예를 들어 총 31개의 페이지가 있다  1~10, 11~20, 21~30, 31  여기서 31은 totalPage가 된다. 10,20,30 페이지는 tempEnd
        end = totalPage > tempEnd ? tempEnd : totalPage;
        // 마지막 페이지가 가상의 마지막 페이지 보다 크면 tempEnd(현재 마지막 페이지) 작으면 totalPage

        next = totalPage > tempEnd; // next 버튼은 마지막 페이지보다 크면 true

        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }

}
