package com.company.member.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.company.member.model.vo.Member;
import com.company.member.service.MemberService;


@Controller
public class MemberController {

	@Autowired 
	private MemberService memberSerivce;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@GetMapping("login")
	public ModelAndView login(ModelAndView mv) {
		mv.setViewName("/login/login");
		logger.info("로그인 페이지에 온걸 환영하고 logger");
		return mv;
	}
	@PostMapping("login")
	public String login(ModelAndView mv,
			HttpSession session, 
			HttpServletRequest request,
			@RequestParam(value ="userId")String id,
			@RequestParam(value = "userPwd")String pwd,
			HttpServletResponse response) {
		
		
		Member vo = new Member(id, pwd);
		String viewName = "";
		
		//System.out.println("입력한 id값: " + id);
		//System.out.println("입력한 pwd값: " + pwd);
		try {
			Member login = memberSerivce.login(vo);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=UTF-8");
			if(login != null) {
				
				session.setAttribute("member", login);
				
				//out.println("<script>alert('로그인에 성공하였습니다.'); </script>");
				logger.info("로그인 성공");
				//mv.setViewName("redirect:/board");
			}else{
				
				//out.println("<script>alert('로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요');   </script>");
				logger.info("로그인 실패");
				//mv.setViewName("redirect:/login");
				
			}
			
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		//return mv;
		return "home";
	}
}
