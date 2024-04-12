package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query("DELETE FROM Reply  r WHERE r.board.bno=:bno")
    void deleteByBno(Long bno);
    // 게시물 삭제시 이용 rno를 직접적으로 지우는 것이 아니라 fk인 bno를 보고 해당 게시물 전체를 삭제
}
