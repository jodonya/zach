package com.zach.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Japheth Odonya
 * @When Dec 8, 2015 2:38:25 AM
 * 
 * Purpose : The Downs (-1) count
 * */
@Document(collection = "commitDowns")
public class CommitDown {
	private String login;
	
	public CommitDown(){}
	
	public CommitDown(String login){
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
