package com.zach.common.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.zach.model.UserLogin;

@Controller

// extends AbstractController
public class OAuthMainController {

//	@Override
//	protected ModelAndView handleRequestInternal(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		
//		
//		
//		ModelAndView model = new ModelAndView("OAuthMainPage");
//		model.addObject("msg", "The Commits");
//		//model.addObject("userLogin", new UserLogin());
//		
//		UserLogin userLogin = (UserLogin)request.getAttribute("userLogin");
//		
//		
//		System.out.println("##### Email is "+userLogin.getEmail());
//
//		return model;
//	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String main(@ModelAttribute("userLogin") UserLogin userLogin, ModelMap model) {
		//model.addAttribute(new UserLogin());
		//return "main";
		model.addAttribute("email", userLogin.getEmail());
		System.out.println(" #### the email is "+userLogin.getEmail());
		
		//Connecting to Github using the GitHub API
		GitHub github = null;;
		try {
			github = GitHub.connectToEnterprise(apiUrl, oauthAccessToken);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GHOrganization ghOrganization = null;
		try {
			ghOrganization = github.getOrganization("zachcollaboration");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PagedIterable<GHUser> listUsers = null;
		try {
			listUsers =ghOrganization.listMembers();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = 0;
		for (GHUser ghUser : listUsers) {
			count++;
			try {
				System.out.println("Member number "+count+" is "+ghUser.getEmail());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		return "OAuthMainPage";

	}

}
