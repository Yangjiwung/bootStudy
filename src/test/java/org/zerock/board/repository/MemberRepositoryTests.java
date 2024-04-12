package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers(){

        IntStream.rangeClosed(1,100).forEach(i ->{ // for문 1~100 만큼
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com") // 이메일 셋팅
                    .password("1111") // 패스워드 셋팅
                    .name("USER"+i) // 이름 셋팅
                    .build();
            memberRepository.save(member);
        });
    }

}
