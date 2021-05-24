package com.example.project01.controller.admin;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.project01.model.member.dto.MemberDTO;
import com.example.project01.service.admin.AdminService;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	
	@Inject 
	AdminService adminService;
	
	@RequestMapping("login.do")
	public String login() {
		return "admin/login"; //로그인페이지 출력
	}
	
	@RequestMapping("login_check.do")
	public ModelAndView login_check(MemberDTO dto, HttpSession session, ModelAndView mav) {
		String name=adminService.loginCheck(dto);
		if(name != null) { //로그인 성공하면 세션변수에 값을 저장하고
			session.setAttribute("admin_userid", dto.getUserid());
			session.setAttribute("admin_name", name);
			session.setAttribute("userid",  dto.getUserid());
			session.setAttribute("name", name);
			mav.setViewName("admin/admin"); //admin.jsp 출력
			mav.addObject("message", "success");
		}else {
			mav.setViewName("admin/login"); //로그인 페이지로 이동
			mav.addObject("message","error");
		}
		return mav;
	}
	
	@RequestMapping("logout.do")
	public String logout(HttpSession session) {
		session.invalidate(); //세션 초기화
		return "redirect:/admin/login.do";
	}
}
