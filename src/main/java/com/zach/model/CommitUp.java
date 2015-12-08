package com.zach.model;

import org.springframework.data.mongodb.core.mapping.Document;
/***
 * @author Japheth Odonya
 * @When Dec 8, 2015 2:38:46 AM
 * 
 * Purpose : The Ups (+1) counts
 * */
@Document(collection = "commitUps")
public class CommitUp {
	private String login;

	public CommitUp(){}
	
	public CommitUp(String login){
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
}
