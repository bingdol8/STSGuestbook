package com.example.project01.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.example.project01.model.board.dao.ReplyDAO;
import com.example.project01.model.board.dto.ReplyDTO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Inject
	ReplyDAO replyDao;
	
	@Override
	public List<ReplyDTO> list(Integer bno, int start, int end, HttpSession session) {
		List<ReplyDTO> items=replyDao.list(bno, start, end); // 댓글 리스트
		String userid=(String)session.getAttribute("userid"); //로그인한 사용자의 아이디
		for(ReplyDTO dto : items) { 
			if(dto.getSecret_reply().equals("y")) { //비밀댓글인 경우
				if(userid == null) { //로그인하지 않은 경우
					dto.setReplytext("비밀댓글입니다.");
				}else { //로그인한 경우
					String writer=dto.getWriter(); // 게시물 작성자
					String replyer=dto.getReplyer(); // 댓글 작성자
					if(!userid.equals(writer) && !userid.equals(replyer)) {
						dto.setReplytext("비밀댓글입니다.");
					}
				}
			}
		}
		return items;
	}

	@Override
	public int count(int bno) {
		return replyDao.count(bno);
	}

	@Override
	public void create(ReplyDTO dto) {
		replyDao.create(dto);
	}

	@Override
	public void update(ReplyDTO dto) {
		replyDao.update(dto);
	}

	@Override
	public void delete(Integer rno) {
		replyDao.delete(rno);
	}

	@Override
	public ReplyDTO detail(int rno) {
		return replyDao.detail(rno);
	}

}
