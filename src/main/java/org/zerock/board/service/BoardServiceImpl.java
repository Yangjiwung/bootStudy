package org.zerock.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.ReplyRepository;

import java.util.function.Function;

@Service
@RequiredArgsConstructor // 의존성 강제주입
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository; // 댓글의 기능사용

    @Override
    public Long register(BoardDTO dto) {

        log.info("객체 확인 : " + dto);

        Board board = dtoToEntity(dto); // dto를 Entity로 변환하는 메서드

        boardRepository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        log.info(pageRequestDTO);

        //Function<Object[], BoardDTO> fn = ... 부분은 Object 배열을 받아서 BoardDTO를 반환하는 함수를 정의합니다.
        // 이 함수는 각각의 Object 배열에 있는 엔티티를 DTO로 변환하는 역할을 합니다.
        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0], (Member)en[1], (Long)en[2]));

        //Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result =boardRepository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );

        return  new PageResultDTO<>(result,fn);
    }

    @Override
    public BoardDTO get(Long bno) {

        Object result = boardRepository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDTO((Board)arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Override
    @Transactional
    public void modify(BoardDTO boardDTO) {

        Board board = boardRepository.getOne(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        boardRepository.save(board);

    }

    @Override
    @Transactional // 게시물 삭제처리는 댓글과 함께 삭제처리가 되던 해야한다.
    public void removeWithReplies(Long bno) {

        // 댓글 삭제
        replyRepository.deleteByBno(bno);
        // 게시물 삭제
        boardRepository.deleteById(bno);

    }
}
