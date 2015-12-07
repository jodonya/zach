package com.zach.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommit.File;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zach.common.config.MongoConfiguration;
import com.zach.model.Commit;
import com.zach.model.CommitComment;
import com.zach.model.CommitFile;
import com.zach.model.UserLogin;
import com.zach.repository.CommitRepository;

@Controller
/***
 * @author Japheth Odonya
 * */
public class OAuthMainController {

	@Value("${token}")
	private String OAUTHACCESSTOKE;

	@Value("${organizationname}")
	private String ORGANIZATIONNAME;

	@Autowired
	private CommitRepository commitRepository;

	@Autowired
	MongoConfiguration mongoConfiguration;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String main(@ModelAttribute("userLogin") UserLogin userLogin,
			ModelMap model) {
		model.addAttribute("email", userLogin.getEmail());
		System.out.println(" #### the email is " + userLogin.getEmail());

		if ((userLogin.getEmail() == null) || userLogin.getEmail().equals("")) {
			// Go back to the main page
			model.addAttribute(new UserLogin());
			// return "main";
			return "GitOAuthPage";
		}

		// Connecting to Github using the GitHub API
		GitHub github = null;
		try {
			github = GitHub.connectUsingOAuth(OAUTHACCESSTOKE);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		GHOrganization ghOrganization = null;
		try {
			ghOrganization = github.getOrganization(ORGANIZATIONNAME);

			// github.getOrganization(ORGANIZATIONNAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// .get(new Coordinates.Simple(ORGANIZATIONNAME)).;
		//
		Map<String, GHRepository> mapRepositories = null;
		try {
			mapRepositories = ghOrganization.getRepositories();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Commit> listTheCommits = new ArrayList<Commit>();
		int sequence = 0;
		List<GHCommit> listCommits = null;
		;
		for (Map.Entry<String, GHRepository> repository : mapRepositories
				.entrySet()) {
			System.out.println("Repository ... "
					+ repository.getValue().getName());

			System.out.println("Home page : "
					+ repository.getValue().getHomepage());
			System.out.println("Repo URL : " + repository.getValue().getUrl());

			System.out.println();
			listCommits = repository.getValue().listCommits().asList();
			for (GHCommit ghCommit : listCommits) {
				sequence++;
				try {
					// ghCommit.getLastStatus().
					System.out.println("Commit # " + sequence + " Author "
							+ ghCommit.getAuthor().getLogin() + " message "
							+ ghCommit.getCommitShortInfo().getMessage()
							+ " hash " + ghCommit.getSHA1());
					listTheCommits.add(new Commit(ghCommit.getAuthor()
							.getLogin(), ghCommit.getCommitShortInfo()
							.getMessage(), ghCommit.getSHA1(), repository
							.getValue().getName()));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				MongoTemplate mongoTemplate = null;
				try {
					mongoTemplate = mongoConfiguration.mongoTemplate();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Commit item = null;

				item = mongoTemplate.findOne(new Query(Criteria.where("_id")
						.is(ghCommit.getSHA1().trim())), Commit.class,
						"commits");
				try {
					if (item == null) {
						// Build the changes
						GHCommit anotherCommit = repository.getValue()
								.getCommit(ghCommit.getSHA1());
						List<CommitFile> listFiles = new ArrayList<CommitFile>();

						for (File file : anotherCommit.getFiles()) {
							if (file.getPatch() != null) {
								listFiles.add(new CommitFile(
										file.getFileName(), file.getStatus(),
										file.getPatch()));
							}
						}

						commitRepository.insert(new Commit(ghCommit.getSHA1(),
								ghCommit.getAuthor().getLogin(), ghCommit
										.getCommitShortInfo().getMessage(),
								ghCommit.getSHA1(), repository.getValue()
										.getName(), listFiles));
					} else {
						System.out.println(" The item already exists !!!  "
								+ ghCommit.getSHA1());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		PagedIterable<GHUser> listUsers = null;
		try {
			listUsers = ghOrganization.listMembers();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int count = 0;
		for (GHUser ghUser : listUsers.asList()) {
			count++;
			System.out.println("Member number " + count + " is " + ghUser);

		}

		model.addAttribute("listTheCommits", commitRepository.findAll());
		return "OAuthMainPage";
	}

	// Get the Diff for a commit
	@RequestMapping(value = "/diff/{commitHash}", method = RequestMethod.GET)
	public String diff(@ModelAttribute("commitComment") CommitComment commitComment,
			@PathVariable("commitHash") String commitHash, ModelMap model) {
		model.addAttribute("email", model.get("email"));
		System.out.println(" #### the email is " + model.get("email"));

		if ((commitHash == null) || commitHash.equals("")) {
			// Go back to the main page
			model.addAttribute(new UserLogin());
			// return "main";
			return "GitOAuthPage";
		}

		// Get the list of files
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Commit commit = (Commit) mongoTemplate.findOne(new Query(Criteria
				.where("_id").is(commitHash.trim())), Commit.class, "commits");

		// commitRepository.
		model.addAttribute("commitMessage", commit.getMessage());
		model.addAttribute("theFiles", commit.getCommitFiles());
		
		model.addAttribute(new CommitComment());

		return "DiffPage";

	}

}
