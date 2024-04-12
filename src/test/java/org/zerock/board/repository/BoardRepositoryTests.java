package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){

        IntStream.rangeClosed(1,100).forEach(i -> {

            Member member = Member.builder().email("user"+ i + "@aaa.com").build();
            // 멤버 객체 생성 1~100 만큼

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content...." + i)
                    .writer(member) // 위에 생성한 멤버를 넣음
                    .build();
            boardRepository.save(board);
        });

    }

    @Test
    @Transactional
    public void testRead1(){

        Optional<Board> result = boardRepository.findById(100L); // DB에서 100 번째 게시물을 뽑아옴

        Board board = result.get(); // 보드 객체에 가져와서 넣음

        System.out.println(board);
        System.out.println(board.getWriter()); // 연관관계 @Transactional 필수
    }

    @Test
    public  void  testReadWithWriter(){

        Object result = boardRepository.getBoardWithWriter(100L); // 내가 만든 메서드
        // 100 번 게시물의 정보와 Member의 Writer를 필요한 정보만 가져옴

        Object[] arr = (Object[]) result; //???
        System.out.println("----------------------");
        System.out.println(Arrays.toString(arr)); // 배열을 문자열로 변환
    }

    @Test
    public void testBoardWithReply(){

        List<Object[]> result = boardRepository.getBoardWithReply(100L);
        // 100 번게시물 댓글객체와 조인하여 가져옴 연관 관계가 없으므로 ON으로 불러옴
        for (Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }

    }

    @Test
    public void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        // 페이징처리 페이지당 10 개씩  bno를 기준으로 내림차순

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;

            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3(){
        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

}
