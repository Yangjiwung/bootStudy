package org.zerock.board.service;

import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    // list처리

    BoardDTO get(Long bno);
    // 상세보기 처리
    
    void modify(BoardDTO boardDTO);
    // dto를 받아 수정처리 Impl 가서 변환 작업

    void removeWithReplies(Long bno);
    // 삭제처리 댓글 삭제 와 게시물 삭제가 동시에 일어남

    // dto -> Entity
    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        // 보드에 작성자와 연결하는 작업 dto의 작성자를 멤버 객체에 삽입하여 맞는 Pk를 찾음

        // 보드 객체에 세팅 멤버 객체를 가져와 넣음
        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;

    }

    // Entity -> dto
    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) // long 으로 나옴 int로 처리
                .build();

        return boardDTO;
    }




}
