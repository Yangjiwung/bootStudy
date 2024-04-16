package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Guestbook;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>,
                                             QuerydslPredicateExecutor<Guestbook> {
    // extends JpaRepository<엔티티명, pk의 타입>
    // jpa가 crud를 알아서 해준다. -> JpaRepository에 상속 받은 메서드
    // insert 작업 : save(엔티티 객체)
    // select 작업 : findById(키 타입), getOne(키 타입)--지금은 차단됨
    // update 작업 : save(엔티티 객체)
    // delete 작업 : deleteById(키 타입),  delete(엔티티 객체)

    // Querydsl : Q도메인을 이용해서 자동으로 검색 조건을 완성 시킴 (다중 검색용)
    // http://querydsl.com/ -> 참고하여 api 의존성 주입을 받아야 한다.
    // , QuerydslPredicateExecutor<Guestbook> 인터페이스는 다중 상속 됨(Qdomain 사용)


}
