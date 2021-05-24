package com.example.project01.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project01.model.board.dao.BoardDAO;
import com.example.project01.model.board.dto.BoardDTO;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	BoardDAO boardDao;
	
	@Override
	public void deleteFile(String fullName) {
		boardDao.deleteFile(fullName);
	}

	@Override
	public List<String> getAttach(int bno) {
		return boardDao.getAttach(bno);
	}

	@Transactional //트랜잭션 처리 - 모두 성공하면 처리, 아니면 롤백
	@Override
	public void create(BoardDTO dto) throws Exception {
		String title=dto.getTitle();
		String writer=dto.getWriter();
		String content=dto.getContent();
		//태그문자 처리
		title=title.replace("<", "&lt;");
		title=title.replace(">", "&gt;");
		writer=writer.replace("<", "&lt;");
		writer=writer.replace(">", "&gt;");
		content=content.replace("<", "&lt;");
		content=content.replace(">", "&gt;");
		//공백문자 처리
		title=title.replace("  ", "&nasp;&nbsp;");
		writer=writer.replace("  ", "&nasp;&nbsp;");
		//줄바꿈 처리
		content=content.replace("\n", "<br>");
		dto.setContent(content);
		dto.setWriter(writer);
		dto.setTitle(title);
		boardDao.create(dto); //레코드 저장
		String[] files=dto.getFiles(); //첨부파일 리스트
		if(files==null) return; //첨부파일이 없으면 종료
		for(String name : files) {
			boardDao.addAttach(name); //첨부파일 목록 저장
		}
	}

	@Override
	public BoardDTO read(int bno) throws Exception {
		return boardDao.read(bno);
	}

	@Transactional //트랜잭션 처리
	@Override
	public void update(BoardDTO dto) throws Exception {
		boardDao.update(dto); //글 수정
		//첨부파일 수정
		String[] files=dto.getFiles();
		if(files==null) return;
		for(String name : files) {
			boardDao.updateAttach(name, dto.getBno());
		}
	}

	@Override
	public void delete(int bno) throws Exception {
		boardDao.delete(bno);
	}

	@Override
	public List<BoardDTO> listAll(int start, int end, String search_option, String keyword) throws Exception {
		return boardDao.listAll(start, end, search_option, keyword);
	}

	@Override //조회수 증가 처리
	public void increaseViewcnt(int bno, HttpSession session) throws Exception {
		long update_time=0;
		if(session.getAttribute("update_time_"+bno) != null) {
			update_time=(long)session.getAttribute("update_time_"+bno);
		}
		long current_time=System.currentTimeMillis();
		if(current_time - update_time > 5*1000) { //지정된 시간 경과
			boardDao.increaseViewcnt(bno);
			session.setAttribute("update_time_"+bno, current_time);
		}
	}

	@Override
	public int countArticle(String search_option, String ketword) throws Exception {
		return boardDao.countArticle(search_option, ketword);
	}

}
