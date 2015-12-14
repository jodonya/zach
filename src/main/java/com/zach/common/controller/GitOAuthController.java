package com.zach.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.zach.model.UserLogin;

@Controller
@SessionAttributes("userLogin")
@RequestMapping("/old")
//extends AbstractController 
public class GitOAuthController {
	
//	@Value("${clientid}")
//	private String CLIENTID;

	
	@RequestMapping(value = "/old", method = RequestMethod.GET)
	public String main(ModelMap model) {
		model.addAttribute(new UserLogin());
		model.addAttribute("clientId", "");
		
		//if (model)
		
		System.out.println("Client Id is ### "+"");
		//return "main";
		return "GitOAuthPage";

	}

}