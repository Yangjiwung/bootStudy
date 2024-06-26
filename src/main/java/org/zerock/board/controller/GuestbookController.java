package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.GuestbookDTO;
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


    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
       log.info("GuestbookController.list() 메서드 실행..." + pageRequestDTO);
        
       // 모델 영역에 리스트 저장
        model.addAttribute("result", service.getList(pageRequestDTO));
    }


    @GetMapping("/register")
    public void register(){
        log.info("register 페이지 불러오는 중....");

    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto : " + dto);

        // 새로 추가된 엔티티의 번호
        Long gno = service.register(dto);

        // 일회성 성공 메세지를 보내기 위해 addFlashAttribute 저장
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";


    }

    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        log.info(gno);

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto", dto);
    }


    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){

        log.info("gno : " + gno);

        service.remove(gno);

        // 일회성 메세지 출력용 셋팅
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";

    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO, 
                         RedirectAttributes redirectAttributes){
        //  @ModelAttribute("requestDTO") PageRequestDTO requestDTO : 프론트 페이지에서 page번호를 받음

        log.info("post modify..............................");
        log.info("dto : " + dto);
        
        service.modify(dto);
        
        // 수정 처리 후 원래 페이지로 돌아가기 위해 페이지 번호 및 검색 조건 type과 key를 같이 넣음
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }




}
