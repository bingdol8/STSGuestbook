package com.example.project01.model.member.dto;

import java.util.Date;

public class MemberDTO {
	private String userid;
	private String passwd;
	private String name;
	private String email;
	private Date join_date;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getPost_date() {
		return join_date;
	}
	public void setPost_date(Date post_date) {
		this.join_date = post_date;
	}
	@Override
	public String toString() {
		return "MemberDTO [userid=" + userid + ", passwd=" + passwd + ", name=" + name + ", email=" + email
				+ ", post_date=" + join_date + "]";
	}
}
