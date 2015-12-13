package com.zach.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/***
 * @author Japheth Odonya
 * @When Dec 13, 2015 2:34:36 PM
 * 
 * Purpose : User Configuration Document for managing user settings
 * */
@Document(collection = "users")
public class UserProfile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String email;
	
	private String clientId;
	private String clientSecret;
	
	private String firstName;
	private String middleName;
	private String lastName;
	
	private List<String> listOrganizations;
	private List<String> listRepositories;
	
	public UserProfile(){}
	
	public UserProfile(String login, String email, String clientId, String clientSecret){
		this.login = login;
		this.email = email;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public List<String> getListOrganizations() {
		return listOrganizations;
	}
	public void setListOrganizations(List<String> listOrganizations) {
		this.listOrganizations = listOrganizations;
	}
	public List<String> getListRepositories() {
		return listRepositories;
	}
	public void setListRepositories(List<String> listRepositories) {
		this.listRepositories = listRepositories;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
