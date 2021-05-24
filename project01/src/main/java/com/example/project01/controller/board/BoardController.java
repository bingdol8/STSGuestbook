package com.example.project01.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.project01.model.board.dto.BoardDTO;
import com.example.project01.service.board.BoardService;
import com.example.project01.service.board.Pager;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	@Inject
	BoardService boardService;
//	@Inject
//	ReplyService replyService;
	
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam(defaultValue="1") int curPage,
			@RequestParam(defaultValue="all") String search_option,
			@RequestParam(defaultValue="") String keyword) throws Exception{
		int count=boardService.countArticle(search_option, keyword); //레코드 개수
		Pager pager=new Pager(count, curPage);
		int start=pager.getPageBegin(); //해당 페이지의 레코드 시작번호
		int end=pager.getPageEnd(); //해당 페이지의 레코드 끝번호
		List<BoardDTO> list=boardService.listAll(start, end, search_option, keyword);
		ModelAndView mav=new ModelAndView();
		mav.setViewName("board/list"); //jsp page 의 이름
		Map<String,Object> map=new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("pager", pager);
		mav.addObject("map", map); //객체 추가 
		return mav; //list.jsp 페이지가 출력됨
	}
	
	@RequestMapping(value="write.do", method=RequestMethod.GET)
	public String write() {
		return "board/write";  // views > write.jsp
	}
	
	@RequestMapping(value="insert.do", method=RequestMethod.POST)
	public String insert(BoardDTO dto, HttpSession session) throws Exception {
		String writer=(String)session.getAttribute("userid");
		dto.setWriter(writer);
		boardService.create(dto);
		return "redirect:/board/list.do";
	}
	//게시물 내용
	@RequestMapping(value="view.do", method=RequestMethod.GET)
	public ModelAndView view(int bno, int curPage, String search_option, String keyword, HttpSession session) throws Exception {
		boardService.increaseViewcnt(bno, session); //조회수 증가 처리
		ModelAndView mav=new ModelAndView();
		mav.setViewName("board/view"); //view의 이름
		mav.addObject("dto", boardService.read(bno));
//		mav.addObject("count", replyService.count(bno));
		mav.addObject("curPage", curPage);
		mav.addObject("search_option", search_option);
		mav.addObject("keyword", keyword);
		return mav;
	}
	
	@RequestMapping("update.do")
	public String update(BoardDTO dto) throws Exception { //레코드 수정
		boardService.update(dto);
		return "redirect:/board/list.do";
	}
	
	@RequestMapping("delete.do")
	public String delete(int bno) throws Exception { //레코드 삭제
		boardService.delete(bno);
		return "redirect:/board/list.do";
	}
	
	@RequestMapping("getAttach/{bno}") //rest형식의 uri
	@ResponseBody // 페이지 리턴이 아닌 데이터 리턴
	public List<String> getAttach(@PathVariable("bno") int bno){
		return boardService.getAttach(bno);
	}
}
