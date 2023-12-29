package kr.ezen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ezen.pwEncoder.domain.ReplyDTO;
import kr.ezen.pwEncoder.domain.ReplyPageDTO;
import kr.ezen.service.ReplyService;

@RestController // @Controller + @ResponseBody
@RequestMapping("/replies")
public class ReplyController {
	@Autowired
	private ReplyService service;
	
	// 댓글 등록
	@PostMapping("/new")
	public String create(@RequestBody ReplyDTO rDto) {
		int n = service.register(rDto);
		
		return n == 1 ? "success":"fail";
	}
	// 댓글 조회
	@GetMapping("/{rno}")
	public ReplyDTO read(@PathVariable("rno") int rno) {
		System.out.println("rno : "+rno);
//		ReplyDTO rDto = service.read(rno);
//		return rDto;
		
		return service.read(rno);
	}
	
	// 댓글 수정
	@PutMapping("/{rno}")
	public String modify(@PathVariable("rno") int rno,
			@RequestBody ReplyDTO rDto) {
		
		rDto.setRno(rno);
		int n = service.modify(rDto);
		return n == 1 ? "success":"fail";
	}
	
	// 댓글 삭제
	@DeleteMapping("/{rno}")
	public String remove(@PathVariable("rno") int rno) {
		int n = service.remove(rno);
		return n == 1 ? "success":"fail";
	}
	
	// 특정 게시글에 대한 댓글리스트
//	@GetMapping("/list/{bid}")
//	public List<ReplyDTO> getList(@PathVariable("bid") int bid){
//		List<ReplyDTO> list = service.getList(bid);
//		
//		return list;
//	}
	
	@GetMapping("/list/{bid}/{viewPage}")
//	public List<ReplyDTO> getList(@PathVariable("bid") int bid, 
//			@PathVariable("viewPage") int vp){
	public ReplyPageDTO getList(@PathVariable("bid") int bid, 
			@PathVariable("viewPage") int vp){
		
		ReplyPageDTO rPageDTO = service.getList(bid, vp);
		
		// 댓글 리스트만 전달
//		return list;
		
		// 페이징 처리를 위해서는 
		// ReplyPageDTO 값을 같이 넘겨야 한다.
		
		return rPageDTO;
	}
	
	
}
