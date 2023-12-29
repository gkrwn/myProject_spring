package kr.ezen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ezen.pwEncoder.domain.BoardDTO;
import kr.ezen.pwEncoder.domain.PageDTO;
import kr.ezen.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	// 게시글 등록폼
	@GetMapping("/register.do")
	public String register() {
		return "board/register";
	}
	
	@PostMapping("/register.do")
	public String register(BoardDTO dto) {
//		for(int i=1; i<=113; i++) {
//			BoardDTO bDto = new BoardDTO();
//			bDto.setSubject(i + "번째 제목입니다~~");
//			bDto.setContents(i + "번째 내용입니다~~");
//			bDto.setWriter("아무개" + (i%10));
//			service.register(bDto);
//		}
		
		service.register(dto);
		return "redirect:/board/list.do";
	}	
	
//	@GetMapping("/list.do")
//	public String list(Model model) {
//		List<BoardDTO> list= service.getList();
//		System.out.println(list);
//		
//		model.addAttribute("list", list);
//		
//		return "board/boardList";
//	}
	
	// 페이징 처리
	@RequestMapping("/list.do")
	public String list(PageDTO pDto, Model model) {
		List<BoardDTO> list= service.getList(pDto);
		System.out.println(list);		
		model.addAttribute("list", list);		
		// 바인딩되는 pDto는 serviceImpl에서 셋팅된 pDto
		model.addAttribute("pDto", pDto);
		
		return "board/boardList";
	}
	
	@GetMapping("/view.do")
	public String view(int bid, String viewPage, @ModelAttribute("pDto") PageDTO pDto, Model model) {
		System.out.println("#####"+viewPage);
		BoardDTO dto = service.view(bid, "v");
		System.out.println(dto);
		model.addAttribute("dto", dto);
//		model.addAttribute("pDto", pDto);
		
		return "board/view";
	}
	
	// 수정 폼페이지 이동
	@GetMapping("/modify.do")
	public String modifyForm(PageDTO pDto, int bid, Model model) {
		BoardDTO dto = service.view(bid, "m");
		model.addAttribute("dto", dto);
		model.addAttribute("pDto", pDto);
		
		return "board/modify";
	}
	
	// 게시글 수정
	@PostMapping("/modify.do")
	public String modify(PageDTO pDto, BoardDTO dto, Model model) {
		service.modify(dto);
		int viewPage = pDto.getViewPage();
		// 리다이렉트시에 model 바인딩을 할 경우 자바객체는 쿼리스트링으로 전달 안된다.
		// int, String인 경우에는 쿼리스트링으로 전달 가능
//		model.addAttribute("pDto", pDto); 
		model.addAttribute("viewPage", viewPage);
		model.addAttribute("searchType", pDto.getSearchType());
		model.addAttribute("keyword", pDto.getKeyword());
		model.addAttribute("cntPerPage", pDto.getCntPerPage());
		
		// model 바인딩된 값을 redirect 할 경우에는 쿼리스트링으로 전달된다.
		return "redirect:list.do";
	}
	
	// 게시글 삭제
	@GetMapping("/remove.do")
	public String remove(PageDTO pDto, int bid, Model model) {	
		service.remove(bid);
//		int viewPage = pDto.getViewPage();
		model.addAttribute("viewPage", pDto.getViewPage());	 	
		
		return "redirect:list.do";
	}
	
}
