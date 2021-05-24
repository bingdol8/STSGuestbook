package com.example.project01.controller.member;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.project01.model.member.dto.MemberDTO;
import com.example.project01.service.member.MemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	@Inject
	MemberService memberService;
	
	@RequestMapping("login.do")
	public String login() {
		return "member/login"; //login.jsp 로 이동
	}
	
	@RequestMapping("login_check.do")
	public ModelAndView login_check(@ModelAttribute MemberDTO dto, HttpSession session) {
		String name=memberService.loginCheck(dto, session);
		ModelAndView mav=new ModelAndView();
		if(name!=null) { //로그인 성공
			mav.setViewName("home"); //home.jsp
		}else { //로그인 실패
			mav.setViewName("member/login"); //login.jsp
			mav.addObject("message", "error");
		}
		return mav;
	}
	
	@RequestMapping("logout.do")
	public ModelAndView logout(HttpSession session, ModelAndView mav) {
		memberService.logout(session);
		mav.setViewName("member/login");
		mav.addObject("message", "logout");
		return mav;
	}
	
	@RequestMapping("join.do")
	public String join() {
		return "member/join";
	}
	
	@RequestMapping("insert.do")
	public String insert(String userid, String passwd, String name, String email) throws Exception {
		MemberDTO dto=new MemberDTO();
		dto.setUserid(userid);
		dto.setPasswd(passwd);
		dto.setName(name);
		dto.setEmail(email);
		memberService.insert(dto);
		return "member/login";
	}
}
