package com.zach.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONObject;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommit.File;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.zach.common.config.MongoConfiguration;
import com.zach.model.AppClient;
import com.zach.model.Commit;
import com.zach.model.CommitComment;
import com.zach.model.CommitDown;
import com.zach.model.CommitFile;
import com.zach.model.CommitUp;
import com.zach.model.UserLogin;
import com.zach.model.UserProfile;
import com.zach.repository.ClientRepository;
import com.zach.repository.CommitRepository;
import com.zach.repository.UserProfileRepository;

/***
 * @author Japheth Odonya
 * */
@SessionAttributes({ "email", "accessToken", "code", "clientId" })
@Controller
public class OAuthMainController {

	// @Value("${token}")
	// private String OAUTHACCESSTOKE;
	//
	// @Value("${clientid}")
	// private String CLIENTID;
	//
	// @Value("${clientsecret}")
	// private String CLIENTSECRET;
	//
	// @Value("${organizationname}")
	// private String ORGANIZATIONNAME;

	@Autowired
	private CommitRepository commitRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	MongoConfiguration mongoConfiguration;

	/**
	 * @author Japheth Odonya
	 * @When Dec 12, 2015 4:55:35 PM
	 * */
	// @RequestMapping(value = "/login", method = RequestMethod.GET)
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(@RequestParam Map<String, String> allRequestParams,
			ModelMap model) {
		String accessToken = null;
		String code = null;

		if ((model.get("accessToken") != null)
				&& (!model.get("accessToken").equals(""))) {
			accessToken = (String) model.get("accessToken");
		} else {
			for (Map.Entry<String, String> parameterSet : allRequestParams
					.entrySet()) {
				// iterable_element.getValue()
				System.out.println("KKKKKKKK Key is " + parameterSet.getKey()
						+ " Value is " + parameterSet.getValue());
				if (parameterSet.getKey().equals("code")) {
					code = parameterSet.getValue();
				}

			}

			accessToken = getAccessToken(code, accessToken);

		}

		// Now get the email address
		String email = null;

		if ((model.get("email") != null) && (!model.get("email").equals(""))) {
			email = (String) model.get("email");
		} else {
			email = getEmailAddress(accessToken);
		}

		GitHub github = null;
		try {
			github = GitHub.connectUsingOAuth(accessToken);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// GHOrganization ghOrganization = null;
		// try {
		// ghOrganization = github.getOrganization(ORGANIZATIONNAME);
		//
		// // github.getOrganization(ORGANIZATIONNAME);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// .get(new Coordinates.Simple(ORGANIZATIONNAME)).;
		//
		Map<String, GHRepository> mapRepositories = null;
		try {
			mapRepositories = github.getMyself().getAllRepositories();
			// mapRepositories = ghOrganization.getRepositories();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Commit> listTheCommits = new ArrayList<Commit>();
		int sequence = 0;
		List<GHCommit> listCommits;
		;
		for (Map.Entry<String, GHRepository> repository : mapRepositories
				.entrySet()) {
			System.out.println("Repository ... "
					+ repository.getValue().getName());

			System.out.println("Home page : "
					+ repository.getValue().getHomepage());
			System.out.println("Repo URL : " + repository.getValue().getUrl());
			listCommits = null;
			System.out.println();
			if (repository.getValue().listCommits() != null)
				listCommits = repository.getValue().listCommits().asList();
			
			if (listCommits != null)
			for (GHCommit ghCommit : listCommits) {
				sequence++;
				try {
					// ghCommit.getLastStatus().
					if (ghCommit.getAuthor() == null) {
						System.out.println(" The Athor is null .... ");
					}

					if (ghCommit.getCommitShortInfo() == null) {
						System.out.println(" Commit Short Info is null .... ");
					}// ghCommit.getCommitShortInfo().getMessage()
					String author = "";
					author = (ghCommit.getAuthor() != null) ? ghCommit
							.getAuthor().getLogin() : "No Author";

					String commitMessage = "";
					commitMessage = (ghCommit.getCommitShortInfo() != null) ? ghCommit
							.getCommitShortInfo().getMessage() : "No Message";

					System.out.println("Commit # " + sequence + " Author "
							+ author + " message " + commitMessage + " hash "
							+ ghCommit.getSHA1());

					listTheCommits.add(new Commit(
							ghCommit.getAuthor() != null ? ghCommit.getAuthor()
									.getLogin() : "No Author" + " message ",
							ghCommit.getCommitShortInfo() != null ? ghCommit
									.getCommitShortInfo().getMessage()
									: "No Message", ghCommit.getSHA1(),
							repository.getValue().getName()));

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

						String author = "";
						author = (ghCommit.getAuthor() != null) ? ghCommit
								.getAuthor().getLogin() : "No Author";

						String commitMessage = "";
						commitMessage = (ghCommit.getCommitShortInfo() != null) ? ghCommit
								.getCommitShortInfo().getMessage()
								: "No Message";

						commitRepository.insert(new Commit(ghCommit.getSHA1(),
								author, commitMessage, ghCommit.getSHA1(),
								repository.getValue().getName(), listFiles,
								new ArrayList<CommitUp>(),
								new ArrayList<CommitDown>()));
					} else {
						System.out.println(" The item already exists !!!  "
								+ ghCommit.getSHA1());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		// PagedIterable<GHUser> listUsers = null;
		// try {
		// listUsers = ghOrganization.listMembers();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// int count = 0;
		// for (GHUser ghUser : listUsers.asList()) {
		// count++;
		// System.out.println("Member number " + count + " is " + ghUser);
		//
		// }

		Long countCommits = commitRepository.count();
		model.addAttribute("count", countCommits);
		model.addAttribute("listTheCommits", commitRepository.findAll());
		model.addAttribute("email", email);
		
		if (getClientId() != null)
			model.addAttribute("clientId", getClientId());
		model.addAttribute("accessToken", accessToken);
		return "OAuthMainPage";
	}

	private String getAccessToken(String code, String accessToken) {
		if ((accessToken != null) && (!accessToken.equals("")))
			return accessToken;

		// Accessing Github page starts here
		// Make a Rest call
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource webResource = client.resource(UriBuilder.fromUri(
				"https://github.com/login/oauth/access_token").build());
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		if (getClientId() != null)
			formData.add("client_id", getClientId());
		formData.add("client_secret", getClientSecretId());
		formData.add("code", code);
		formData.add("accept", "json");

		ClientResponse response = webResource
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, formData);
		// WebResource.Builder builder = webResource.getRequestBuilder();
		// ClientResponse response =
		// builder.accept("application/json").get(ClientResponse.class);
		// String json = response.getEntity(String.class);
		String responseMessage = response.getEntity(String.class);
		System.out.println("RRRRR Response " + responseMessage);

		JSONObject json = new JSONObject(responseMessage);

		accessToken = json.getString("access_token").trim();
		return accessToken;
	}

	private String getClientSecretId() {

		// Get active client id
		// Get the list of files
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// If there is no user profile for this user then create on in the
		// database with
		// login and email
		AppClient client;

		client = mongoTemplate.findOne(
				new Query(Criteria.where("active").is("ACTIVE")),
				AppClient.class, "clients");

		return client.getClientSecret();
	}

	/****
	 * @author Japheth Odonya
	 * @When Dec 12, 2015 4:22:25 PM
	 * 
	 *       Get the member email address given the accessToken
	 * */
	private String getEmailAddress(String accessToken) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);

		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("access_token", accessToken);
		WebResource webResource = client.resource(
				UriBuilder.fromUri("https://api.github.com/user/emails")
						.build()).queryParams(formData);
		WebResource.Builder builder = webResource.getRequestBuilder();
		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);//

		String responseMessage = response.getEntity(String.class);
		responseMessage = responseMessage.replace("[", "").replace("]", "");
		// System.out.println("RRRRR Email Response "+responseMessage);
		JSONObject json = new JSONObject(responseMessage);

		return json.getString("email");
	}

	// Get the Diff for a commit
	@RequestMapping(value = "/diff/{commitHash}/{email}/", method = RequestMethod.GET)
	public String diff(
			@ModelAttribute("commitComment") CommitComment commitComment,
			@PathVariable("commitHash") String commitHash,
			@PathVariable("email") String email, ModelMap model) {
		checkLogedIn(model);
		if ((commitHash == null) || commitHash.equals("")) {
			// Go back to the main page
			model.addAttribute(new UserLogin());
			// return "main";
			return "GitOAuthPage";
		}

		System.out.println("The email passed across is ... " + email);

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

		List<CommitComment> theCommentList = commit.getCommitCommentList();

		if ((theCommentList != null) && (theCommentList.size() > 0))
			Collections.reverse(theCommentList);

		model.addAttribute("commitCommentList", theCommentList);
		model.addAttribute("email", model.get("email"));

		model.addAttribute("commitHash", commitHash);
		model.addAttribute("email", email);

		CommitComment newCommitComment = new CommitComment();
		newCommitComment.setCommitHash(commitHash);

		model.addAttribute(newCommitComment);

		return "DiffPage";

	}

	// /up
	@RequestMapping(value = "/up/{commitHash}/{email}/", method = RequestMethod.GET)
	public String up(@PathVariable("commitHash") String commitHash,
			@PathVariable("email") String email, ModelMap model) {
		checkLogedIn(model);

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

		commit.getListCommitUps().add(new CommitUp(email));
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(commitHash.trim()));

		Update update = new Update();
		// List<CommitUp> listCommitUps = commit.getListCommitUps().add(new
		// CommitUp(email));
		update.set("listCommitUps", commit.getListCommitUps());
		mongoTemplate.upsert(query, update, Commit.class, "commits");
		// commitRepository.save(commit);

		// commitRepository.
		model.addAttribute("commitMessage", commit.getMessage());
		// model.addAttribute("theFiles", commit.getCommitFiles());
		model.addAttribute("listTheCommits", commitRepository.findAll());
		model.addAttribute("userLogin", model.get("userLogin"));
		return "OAuthMainPage";

	}

	// down
	@RequestMapping(value = "/down/{commitHash}/{email}/", method = RequestMethod.GET)
	public String down(@PathVariable("commitHash") String commitHash,
			@PathVariable("email") String email, ModelMap model) {
		checkLogedIn(model);

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

		commit.getListCommitDowns().add(new CommitDown(email));
		// commitRepository.save(commit);

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(commitHash.trim()));

		Update update = new Update();
		// List<CommitUp> listCommitUps = commit.getListCommitUps().add(new
		// CommitUp(email));
		update.set("listCommitDowns", commit.getListCommitDowns());
		mongoTemplate.upsert(query, update, Commit.class, "commits");

		// commitRepository.
		model.addAttribute("commitMessage", commit.getMessage());
		// model.addAttribute("theFiles", commit.getCommitFiles());
		model.addAttribute("listTheCommits", commitRepository.findAll());
		model.addAttribute("userLogin", model.get("userLogin"));

		return "OAuthMainPage";

	}

	// addComment
	@RequestMapping(value = "/addComment/{commitHash}/{email}/", method = RequestMethod.POST)
	public String addComment(
			@ModelAttribute("commitComment") CommitComment commitComment,
			@PathVariable("commitHash") String commitHash,
			@PathVariable("email") String email, ModelMap model) {

		checkLogedIn(model);

		// Get the commit and update with comment

		// Get all the comments related to this commit
		// Get the list of files
		MongoTemplate mongoTemplate = null;

		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
			// mongoTemplate.get
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Commit commit = (Commit) mongoTemplate.findOne(new Query(Criteria
				.where("_id").is(commitHash.trim())), Commit.class, "commits");

		if ((commitComment.getComment() == null)
				|| (commitComment.getComment().equals(""))) {
			// commitRepository.
			model.addAttribute("commitMessage", commit.getMessage());
			// model.addAttribute("theFiles", commit.getCommitFiles());
			model.addAttribute("listTheCommits", commitRepository.findAll());

			List<CommitComment> theCommentList = commit.getCommitCommentList();

			if ((theCommentList != null) && (theCommentList.size() > 0))
				Collections.reverse(theCommentList);
			model.addAttribute("commitCommentList", theCommentList);
			model.addAttribute("commitHash", commitHash);
			model.addAttribute("userLogin", model.get("userLogin"));

			model.addAttribute("theFiles", commit.getCommitFiles());
			commitComment = new CommitComment("", commitHash, email, new Date(
					Calendar.getInstance().getTimeInMillis()));
			model.addAttribute("commitComment", commitComment);

			return "DiffPage";
		}

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(commitHash.trim()));

		if (commit.getCommitCommentList() == null) {
			commit.setCommitCommentList(new ArrayList<CommitComment>());
		}

		commit.getCommitCommentList().add(
				new CommitComment(commitComment.getComment(), commitHash,
						email, new Date(Calendar.getInstance()
								.getTimeInMillis())));

		Update update = new Update();
		// List<CommitUp> listCommitUps = commit.getListCommitUps().add(new
		// CommitUp(email));
		update.set("commitCommentList", commit.getCommitCommentList());
		mongoTemplate.upsert(query, update, Commit.class, "commits");

		// commitRepository.
		model.addAttribute("commitMessage", commit.getMessage());
		// model.addAttribute("theFiles", commit.getCommitFiles());
		model.addAttribute("listTheCommits", commitRepository.findAll());

		List<CommitComment> theCommentList = commit.getCommitCommentList();

		if ((theCommentList != null) && (theCommentList.size() > 0))
			Collections.reverse(theCommentList);
		model.addAttribute("commitCommentList", theCommentList);
		model.addAttribute("commitHash", commitHash);
		model.addAttribute("userLogin", model.get("userLogin"));

		model.addAttribute("theFiles", commit.getCommitFiles());
		commitComment = new CommitComment("", commitHash, email, new Date(
				Calendar.getInstance().getTimeInMillis()));
		model.addAttribute("commitComment", commitComment);

		return "DiffPage";
	}

	/***
	 * @author Japheth Odonya
	 * @When Dec 12, 2015 5:49:29 PM
	 * */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(ModelMap model) {

		if ((model.get("accessToken") != null)
				&& (!model.get("accessToken").equals(""))) {
			return "redirect:/login";
		}

		String clientId = getClientId();

		model.addAttribute(new UserLogin());
		
		if (getClientId() != null)
			model.addAttribute("clientId", clientId);

		// if (model)

		System.out.println("Client Id is ### " + clientId);
		// return "main";
		return "GitOAuthPage";

	}

	private String getClientId() {

		// Get active client id
		// Get the list of files
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// If there is no user profile for this user then create on in the
		// database with
		// login and email
		AppClient client;

		client = mongoTemplate.findOne(
				new Query(Criteria.where("active").is("ACTIVE")),
				AppClient.class, "clients");

		if (client == null)
			return null;
		
		return client.getClientId();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		// model.remove("accessToken");
		// model.remove("email");
		// model.remove("code");

		model.addAttribute("accessToken", "");
		model.addAttribute("email", "");
		model.addAttribute("code", "");
		//model.addAttribute("clientId", "");
		
		

		return "redirect:/";

	}

	public String checkLogedIn(ModelMap model) {

		if ((model.get("accessToken") == null)
				|| (model.get("accessToken").equals(""))) {
			return "redirect:/";
		}

		return null;
	}

	// To the user profile
	// Get the Diff for a commit
	@RequestMapping(value = "/myprofile/{email}/", method = RequestMethod.GET)
	public String myprofile(
			@ModelAttribute("userProfile") UserProfile userProfile,
			@PathVariable("email") String email, ModelMap model) {
		checkLogedIn(model);
		if ((email == null) || email.equals("")) {
			// Go back to the main page
			model.addAttribute(new UserProfile());
			// return "main";
			// return "GitOAuthPage";
			return "redirect:/logout";
		}

		System.out.println("The email passed across is ... " + email);

		// Get the list of files
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// If there is no user profile for this user then create on in the
		// database with
		// login and email
		UserProfile userProfileItem = null;

		userProfileItem = mongoTemplate.findOne(
				new Query(Criteria.where("email").is(email.trim())),
				UserProfile.class, "users");

		if (userProfileItem == null) {
			// Create a User Profile
			userProfileItem = new UserProfile(null, email, null, null);
			userProfileRepository.insert(userProfileItem);

			// .insert(new UserProfile(null, email, null, null));
		}

		// Commit commit = (Commit) mongoTemplate.findOne(new Query(Criteria
		// .where("_id").is(commitHash.trim())), Commit.class, "commits");

		// commitRepository.
		model.addAttribute("email", email);
		model.addAttribute("user", userProfileItem);
		model.addAttribute("repositories", new ArrayList<String>());

		// UserProfile newUserProfile = new UserProfile();

		model.addAttribute(userProfileItem);

		return "MyProfile";

	}

	/***
	 * @author Japheth Odonya
	 * @When Dec 13, 2015 2:39:12 PM
	 * 
	 *       Purpose : Managing User Profiles Use User Email and the pertaining
	 *       details
	 * */
	@RequestMapping(value = "/userprofiles/{email}/", method = RequestMethod.GET)
	public String userprofiles(
			@ModelAttribute("userProfile") UserProfile userProfile,
			@PathVariable("email") String email, ModelMap model) {
		checkLogedIn(model);
		if ((email == null) || email.equals("")) {
			// Go back to the main page
			model.addAttribute(new UserProfile());
			// return "main";
			// return "GitOAuthPage";
			return "redirect:/logout";
		}

		System.out.println("The email passed across is ... " + email);

		// Get the list of files
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Commit commit = (Commit) mongoTemplate.findOne(new Query(Criteria
		// .where("_id").is(commitHash.trim())), Commit.class, "commits");

		// commitRepository.

		List<UserProfile> usersList = (List<UserProfile>) mongoTemplate.find(
				null, UserProfile.class, "users");

		model.addAttribute("email", email);
		model.addAttribute("usersList", usersList);

		UserProfile newUserProfile = new UserProfile();

		model.addAttribute(newUserProfile);
		// usersList
		return "UserProfiles";

	}

	// Update user profile
	// updateUserProfile
	@RequestMapping(value = "/updateUserProfile/{email}/", method = RequestMethod.POST)
	public String updateUserProfile(
			@ModelAttribute("userProfile") UserProfile userProfile,
			@PathVariable("email") String email, ModelMap model) {
		checkLogedIn(model);
		if ((email == null) || email.equals("")) {
			// Go back to the main page
			model.addAttribute(new UserProfile());
			// return "main";
			// return "GitOAuthPage";
			return "redirect:/logout";
		}

		System.out.println("The email passed across is ... " + email);

		System.out.println("Updated User Values ... ");

		System.out.println("Updated User Values Email ... "
				+ userProfile.getEmail());
		System.out.println("Updated User Values Login ... "
				+ userProfile.getLogin());
		System.out.println("Updated User Values Client ID ... "
				+ userProfile.getClientId());
		System.out.println("Updated User Values Client Secret... "
				+ userProfile.getClientSecret());

		// Get the list of files
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateUserProfile(userProfile);

		model.addAttribute("email", email);
		model.addAttribute(userProfile);

		return "redirect:/myprofile/" + email + "/";

	}

	private void updateUserProfile(UserProfile userProfile) {
		// TODO Auto-generated method stub

		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBCollection usersCollection = mongoTemplate.getCollection("users");
		// String id = (String)dbObj.get("_id");
		String userEmail = userProfile.getEmail().trim();
		String id = "";
		BasicDBObject query = new BasicDBObject();
		BasicDBObject field = new BasicDBObject();
		field.put("email", userEmail);
		DBCursor cursor = usersCollection.find(query, field);
		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			id = obj.getString("_id");
			// result.add(obj.getString("HomeTown"));
		}

		System.out.println("The query user _id is " + id);

		Query updateQuery = new Query();
		updateQuery.addCriteria(Criteria.where("_id").is(id));

		Update update = new Update();
		update.set("_id", id);
		update.set("email", userEmail);
		update.set("login", userProfile.getLogin());

		update.set("firstName", userProfile.getFirstName());
		update.set("middleName", userProfile.getMiddleName());
		update.set("lastName", userProfile.getLastName());

		update.set("clientId", userProfile.getClientId());
		update.set("clientSecret", userProfile.getClientSecret());
		// List<CommitUp> listCommitUps = commit.getListCommitUps().add(new
		// CommitUp(email));
		mongoTemplate.upsert(updateQuery, update, UserProfile.class, "users");

	}

	// Pulling Commits
	// pullcommits
	/****
	 * @author Japheth Odonya
	 * @When Dec 13, 2015 8:07:00 PM
	 * 
	 *       For pulling commits while logged in
	 * */
	@RequestMapping(value = { "/pullcommits" }, method = RequestMethod.GET)
	public String pullcommits(
			@RequestParam Map<String, String> allRequestParams, ModelMap model) {

		System.out.println("RRRRRRRR Redirecting Commits !!!");
		return "redirect:/login";
	}

	// clientprofile
	/****
	 * @author Japheth Odonya
	 * @When Dec 14, 2015 9:41:23 AM
	 * 
	 *       Client Profile / for adding and updating the client information
	 * */
	@RequestMapping(value = "/clientprofile/{clientId}/", method = RequestMethod.GET)
	public String clientprofile(@ModelAttribute("client") Client client,
			@PathVariable("clientId") String clientId, ModelMap model) {
		// checkLogedIn(model);

		System.out.println("The Client ID  is ... " + clientId);

		// Get the list of files
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// If there is no Client Profile for this Client then create on in the
		// database with
		// login and email
		AppClient clientItem = null;

		clientItem = mongoTemplate.findOne(new Query(Criteria.where("clientId")
				.is(clientId.trim())), AppClient.class, "clients");

		if (clientItem == null) {
			// Create a User Profile
			clientItem = new AppClient(clientId, null, "ACTIVE");
			// userProfileRepository.insert(userProfileItem);
			clientRepository.insert(clientItem);
			// .insert(new UserProfile(null, email, null, null));
		}

		// Commit commit = (Commit) mongoTemplate.findOne(new Query(Criteria
		// .where("_id").is(commitHash.trim())), Commit.class, "commits");

		// commitRepository.
		model.addAttribute("client", clientItem);
		model.addAttribute("repositories", new ArrayList<String>());

		// UserProfile newUserProfile = new UserProfile();

		model.addAttribute(clientItem);

		return "ClientProfile";

	}

	// updateClient
	/***
	 * @author Japheth Odonya
	 * @When Dec 14, 2015 11:14:59 AM
	 * 
	 *       Updating client information
	 * */
	@RequestMapping(value = "/updateClient", method = RequestMethod.POST)
	public String updateClient(@ModelAttribute("client") AppClient client,
			ModelMap model) {

		System.out.println("The client id passed across is ... "
				+ client.getClientId());

		System.out.println("Updated Client Values ... ");

		System.out.println("Updated Client Values clientId ... "
				+ client.getClientId());
		System.out.println("Updated Client Values clientSecret ... "
				+ client.getClientSecret());

		// Get the list of files
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateClient(client);

		model.addAttribute(client);

		return "redirect:/clientprofile/" + client.getClientId() + "/";

	}

	private void updateClient(AppClient client) {
		// TODO Auto-generated method stub

		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBCollection usersCollection = mongoTemplate.getCollection("clients");
		// String id = (String)dbObj.get("_id");
		String clientId = client.getClientId().trim();
		String id = "";
		BasicDBObject query = new BasicDBObject();
		BasicDBObject field = new BasicDBObject();
		field.put("clientId", clientId);
		DBCursor cursor = usersCollection.find(query, field);
		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			id = obj.getString("_id");
			// result.add(obj.getString("HomeTown"));
		}

		System.out.println("The query user _id is " + id);

		Query updateQuery = new Query();
		updateQuery.addCriteria(Criteria.where("_id").is(id));

		Update update = new Update();

		if ((id != null) && (!id.equals(""))) {
			update.set("_id", id);
		}

		update.set("clientId", clientId);
		update.set("active", client.getActive());
		update.set("clientSecret", client.getClientSecret());

		update.set("description", client.getDescription());

		// List<CommitUp> listCommitUps = commit.getListCommitUps().add(new
		// CommitUp(email));
		mongoTemplate.upsert(updateQuery, update, AppClient.class, "clients");

	}
	
	
	@RequestMapping(value = "/clientprofile//", method = RequestMethod.GET)
	public String clientprofileEmptyId(@ModelAttribute("client") Client client,
			ModelMap model) {
		// checkLogedIn(model);


		// Get the list of files
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// If there is no Client Profile for this Client then create on in the
		// database with
		// login and email
		AppClient clientItem = null;
		String clientId = "";

		clientItem = mongoTemplate.findOne(new Query(Criteria.where("clientId")
				.is(clientId.trim())), AppClient.class, "clients");

		if (clientItem == null) {
			// Create a User Profile
			clientItem = new AppClient(clientId, null, "ACTIVE");
			// userProfileRepository.insert(userProfileItem);
			clientRepository.insert(clientItem);
			// .insert(new UserProfile(null, email, null, null));
		}

		// Commit commit = (Commit) mongoTemplate.findOne(new Query(Criteria
		// .where("_id").is(commitHash.trim())), Commit.class, "commits");

		// commitRepository.
		model.addAttribute("client", clientItem);
		model.addAttribute("repositories", new ArrayList<String>());

		// UserProfile newUserProfile = new UserProfile();

		model.addAttribute(clientItem);

		return "ClientProfile";

	}

}
