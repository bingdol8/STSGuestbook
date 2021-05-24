package com.example.project01.controller.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.project01.model.board.dto.ReplyDTO;
import com.example.project01.service.board.Pager;
import com.example.project01.service.board.ReplyService;

@RestController //스프링4.0부터 사용, 뷰와 데이터 리턴 가능
@RequestMapping("/reply/*") //공통적인 url pattern
public class ReplyController {

	@Inject
	ReplyService replyService;
	
	@RequestMapping("insert.do") //댓글 저장
	public void insert(ReplyDTO dto, HttpSession session) {
		String userid=(String)session.getAttribute("userid"); //사용자 아이디
		dto.setReplyer(userid);
		replyService.create(dto);
	}
	
	// delete 삭제를 위한 상태코드
	@RequestMapping(value="/delete/{rno}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rno") int rno){
		ResponseEntity<String> entity=null; // 데이터와 http 상태 코드를 함께 리턴
		try {
			replyService.delete(rno); //레코드 삭제
			entity=new ResponseEntity<>("success",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//ResponseBody : 객체 => json
	//RequestBody : json 형식의 데이터를 객체로 변환
	@RequestMapping(value="insert_rest.do", method=RequestMethod.POST)
	public ResponseEntity<String> insert_rest(@RequestBody ReplyDTO dto, HttpSession session){
		ResponseEntity<String> entity=null;
		try {
			String userid=(String)session.getAttribute("userid");
			dto.setReplyer(userid);
			replyService.create(dto);
			entity=new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//댓글 내용
	@RequestMapping(value="/detail/{rno}", method=RequestMethod.GET)
	public ModelAndView reply_detail(@PathVariable("rno") int rno, ModelAndView mav) {
		ReplyDTO dto=replyService.detail(rno);
		mav.setViewName("board/reply_detail"); //페이지의 이름
		mav.addObject("dto", dto); //출력할 데이터
		return mav;
	}
	
	// rest uri 방식
	//댓글 리스트, 페이지 나누기 처리
	@RequestMapping(value="/list/{bno}/{curPage}", method=RequestMethod.GET)
	public ModelAndView reply_list(@PathVariable("bno") int bno, @PathVariable int curPage,
			ModelAndView mav, HttpSession session) {
		int count=replyService.count(bno);
		Pager pager=new Pager(count, curPage);
		int start=pager.getPageBegin(); //레코드 시작 번호
		int end=pager.getPageEnd(); //레코드 끝 번호
		List<ReplyDTO> list=replyService.list(bno, start, end, session);
		mav.setViewName("board/reply_list");
		mav.addObject("list", list);
		mav.addObject("pager", pager);
		return mav;
	}
	
	@RequestMapping("list.do")
	public ModelAndView list(int bno, @RequestParam(defaultValue="1") int curPage,
			ModelAndView mav, HttpSession session) {
		int count=replyService.count(bno);
		Pager pager=new Pager(count, curPage);
		int start=pager.getPageBegin();
		int end=pager.getPageEnd();
		List<ReplyDTO> list=replyService.list(bno, start, end, session);
		mav.setViewName("board/reply_list");
		mav.addObject("list", list);
		mav.addObject("pager", pager);
		return mav;
	}
	
	// put - 전체 내용 수정, patch - 부분 내용 수정
	@RequestMapping(value="/update/{rno}",
			method= {RequestMethod.PUT, RequestMethod.PATCH} )
	public ResponseEntity<String> update(@PathVariable("rno") int rno,
			@RequestBody ReplyDTO dto){
		ResponseEntity<String> entity=null;
		try {
			dto.setRno(rno);
			replyService.update(dto);
			entity=new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	// RequestBody : 전달된 json 형식의 데이터를 객체로 변환
	@RequestMapping("list_json.do")
	@ResponseBody
	public List<ReplyDTO> list_json(
			@RequestParam(defaultValue="1") int curPage, @RequestParam int bno,
			HttpSession session){
		int count=10;
		Pager pager=new Pager(count, curPage);
		int start=pager.getPageBegin();
		int end=pager.getPageEnd();
		List<ReplyDTO> list=replyService.list(bno, start, end, session);
		return list;
	}
	
}
