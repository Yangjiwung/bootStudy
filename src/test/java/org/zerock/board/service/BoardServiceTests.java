package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){

        BoardDTO dto = BoardDTO.builder()
                .title("Test..")
                .content("Test")
                .writerEmail("user55@aaa.com") // DB에 있는 회원 이메일
                .build();
        Long bno = boardService.register(dto);

    }

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO dto : result.getDtoList()){
            System.out.println(dto);
        }
    }

    @Test
    public void testGet(){

        Long bno = 100L;

        BoardDTO boardDTO =boardService.get(bno);
        System.out.println(boardDTO);
    }


    @Test
    public void testRemove(){
        Long bno = 1L;

        boardService.removeWithReplies(bno);
    }


    @Test
    public void modify(){

        BoardDTO board = BoardDTO.builder()
                .bno(2L)
                .title("제목수정")
                .content("내용수정")
                .build();
        boardService.modify(board);
    }

}
