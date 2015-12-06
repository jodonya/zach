package com.zach.common.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zach.model.UserLogin;

@Controller
/***
 * @author Japheth Odonya
 * */
public class OAuthMainController {

	public static String OAUTHACCESSTOKE = "026aa99777016a7499d3dc76d328b5e58d7213c9";

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String main(@ModelAttribute("userLogin") UserLogin userLogin,
			ModelMap model) {
		model.addAttribute("email", userLogin.getEmail());
		System.out.println(" #### the email is " + userLogin.getEmail());

		// Connecting to Github using the GitHub API
		GitHub github = null;
		try {
			github = GitHub.connectUsingOAuth(OAUTHACCESSTOKE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GHOrganization ghOrganization = null;
		try {
			ghOrganization = github.getOrganization("zachcollaboration");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ghOrganization.getMembers()

		// ghOrganization.get
		// GHRe
		Map<String, GHRepository> mapRepositories = null;
		try {
			mapRepositories = ghOrganization.getRepositories();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int sequence = 0;
		List<GHCommit> listCommits = null;;
		for (Map.Entry<String, GHRepository> repository : mapRepositories
				.entrySet()) {
			listCommits = repository.getValue().listCommits().asList();
			for (GHCommit ghCommit : listCommits) {
				sequence++;
				try {
					System.out.println("Commit # "+sequence+" Author "+ghCommit.getAuthor()+" comment "+ghCommit.getCommitShortInfo()+" date "+ghCommit.getSHA1());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}

		}

		// ghOrganization.getRepositories();

		PagedIterable<GHUser> listUsers = null;
		try {
			listUsers = ghOrganization.listMembers();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(" The number of members is "
				+ listUsers.asList().size());

		int count = 0;
		for (GHUser ghUser : listUsers.asList()) {
			count++;
			// ghUser.get
			System.out.println("Member number " + count + " is " + ghUser);

		}

		return "OAuthMainPage";

	}

}
