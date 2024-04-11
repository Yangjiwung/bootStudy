package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.GuestbookService;

@Controller // 컨트롤러 역할 지정
@RequestMapping("/guestbook") // http://localhost/guestbook/?????
@Log4j2
@RequiredArgsConstructor // 자동 주입을 위한 어노테이션
public class GuestbookController {
    
    private final  GuestbookService service; // final로 선언( 자동주입 )

    @GetMapping("/")
    public String index(){

        return "redirect:/guestbook/list";
    }


    @GetMapping("list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
       log.info("GuestbookController.list() 메서드 실행..." + pageRequestDTO);
        
       // 모델 영역에 리스트 저장
        model.addAttribute("result", service.getList(pageRequestDTO));
    }







}
