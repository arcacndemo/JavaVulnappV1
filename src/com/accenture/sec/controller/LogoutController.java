package com.accenture.sec.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LogoutController {
	
	@GetMapping(value="/logout.html")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		httpSession.invalidate();

		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		mv.addObject("msg", "You are successfully logged out");
		return mv;
	}
}
