package com.example.project01.model.member.dao;

import com.example.project01.model.member.dto.MemberDTO;

public interface MemberDAO {
	public String loginCheck(MemberDTO dto);
	public void insert(MemberDTO dto);
}
