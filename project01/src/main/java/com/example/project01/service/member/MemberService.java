package com.example.project01.service.member;

import javax.servlet.http.HttpSession;

import com.example.project01.model.member.dto.MemberDTO;

public interface MemberService {
	public String loginCheck(MemberDTO dto, HttpSession session);
	public void logout(HttpSession session);
	public void insert(MemberDTO dto);
}
