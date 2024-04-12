package org.zerock.board.service;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.GuestbookDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Guestbook;
import org.zerock.board.entity.QGuestbook;
import org.zerock.board.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository; // 반드시 final로 선언

    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO ---------------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto); // dto를 엔티티로 변환

        log.info(entity);

        repository.save(entity);
        return entity.getGno(); // 실제 DB에 저장된 후에 엔티티가 가지는 gno를 반환
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        // dto를 받아 gno기준으로 내림차순 정렬

        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색 조건 처리

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);
        // dto를 모두 찾아 Page 타입에 넣음

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {

        Optional<Guestbook> result = repository.findById(gno);
        // null값 처리 및 gno를 받아 객체를 찾아옴
        
        return result.isPresent()? entityToDto(result.get()) : null;
        // 만약 findById를 통해 엔티티 객체를 가져왔다면 true 엔티티를 dto로 변환하여 리턴 : false일 경우 null 리턴

    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
        // gno를 받아 삭제처리
    }

    @Override
    public void modify(GuestbookDTO dto) {
        // 업데이트 항목은 "제목", "내용"

        Optional<Guestbook> result = repository.findById(dto.getGno());
        // select 작업을 먼저 처리하여 객체를 가져옴

        if (result.isPresent()){ // Optional 객체가 값을 가지고 있다면 true

            Guestbook entity = result.get(); // dto와 entity객체를 일치시킴

            // 미리만들어 놓은 메서드(세터)를 활용
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity); // gno가 있으면 update 없으면  insert
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){ //Querydsl처리

        String type = requestDTO.getType(); // 제목, 작성자, 내용

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // 객체생성

        QGuestbook qGuestbook =QGuestbook.guestbook;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qGuestbook.gno.gt(0L); // gno > 0

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0){ // 검색조건이 없거나 길이가 0일때
            return booleanBuilder;
        }

        // 검색 조건 작성하기
        BooleanBuilder conditionBulider = new BooleanBuilder();

        if(type.contains("t")){
            conditionBulider.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBulider.or(qGuestbook.content.contains(keyword));
        }
        if (type.contains("w")){
            conditionBulider.or(qGuestbook.writer.contains(keyword));
        }

        // 모든 조건 통합
        booleanBuilder.and(conditionBulider);
        return booleanBuilder;
    }

}
