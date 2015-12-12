package com.zach.common.controller;

import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${clientid}")
	private String CLIENTID;

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(ModelMap model) {
		model.addAttribute(new UserLogin());
		model.addAttribute("clientId", CLIENTID);
		
		System.out.println("Client Id is ### "+CLIENTID);
		//return "main";
		return "GitOAuthPage";

	}

}