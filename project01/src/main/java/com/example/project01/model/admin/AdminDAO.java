package com.example.project01.model.admin;

import com.example.project01.model.member.dto.MemberDTO;

public interface AdminDAO {
	public String loginCheck(MemberDTO dto);
}
