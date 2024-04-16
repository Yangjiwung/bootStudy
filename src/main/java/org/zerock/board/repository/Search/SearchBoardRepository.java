package org.zerock.board.repository.Search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.board.entity.Board;

public interface SearchBoardRepository {

    Board search1();
    // Board 객체와 작성자의 이메일, 댓글의 개수가 출력 됨

    Page<Object[]> searchPage(String type, String keyowrd, Pageable pageable);
    // 원하는 파라미터를 전송하기 위해 Page<Object[]>를 만들어 반환 
}
