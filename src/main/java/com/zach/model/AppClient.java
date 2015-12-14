package com.zach.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")
public class AppClient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientId;
	private String clientSecret;
	private String active;
	private String description;
	
	
	public AppClient(){}
	
	public AppClient(String clientId, String clientSecret, String active){
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.active = active;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
