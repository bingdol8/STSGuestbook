package com.example.project01.model.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.project01.model.board.dto.ReplyDTO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

	@Inject
	SqlSession sqlSession;
	
	@Override
	public List<ReplyDTO> list(Integer bno, int start, int end) {
		Map<String,Object> map=new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		map.put("bno", bno); // 게시물 번호
		return sqlSession.selectList("reply.listReply", map);
	}

	@Override
	public int count(int bno) {
		return sqlSession.selectOne("reply.count", bno);
	}

	@Override
	public void create(ReplyDTO dto) {
		sqlSession.insert("reply.insertReply", dto);
	}

	@Override
	public void update(ReplyDTO dto) {
		sqlSession.update("reply.update", dto);
	}

	@Override
	public void delete(Integer rno) {
		sqlSession.delete("reply.delete", rno);
	}

	@Override
	public ReplyDTO detail(int rno) {
		return sqlSession.selectOne("reply.detail", rno);
	}

}
