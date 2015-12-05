package com.zach.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.zach.model.UserLogin;

@Controller
@SessionAttributes("userLogin")
@RequestMapping("/")
//extends AbstractController 
public class GitOAuthController {

//	@Override
//	protected ModelAndView handleRequestInternal(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		ModelAndView model = new ModelAndView("GitOAuthPage");
//		model.addObject("msg", "Git Commits and Collaboration");
//		model.addObject("userLogin", new UserLogin());
//
//		return model;
//	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(ModelMap model) {
		model.addAttribute(new UserLogin());
		//return "main";
		return "GitOAuthPage";

	}

}