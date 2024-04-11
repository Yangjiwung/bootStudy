package org.zerock.board.service;

import org.zerock.board.dto.GuestbookDTO;
import org.zerock.board.entity.Guestbook;

public interface GuestbookService {

    Long register(GuestbookDTO dto);

    // dto를 받아 엔티티객체로 변환
    default Guestbook dtoToEntity(GuestbookDTO dto){
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }


}
