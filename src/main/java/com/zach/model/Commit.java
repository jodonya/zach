package com.zach.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Japheth Odonya
 * */
@Document(collection = "commits")
public class Commit {
	private String _id;
	private String _class;
	private String id;
	private String login;
	private String message;
	private String hash;
	private String repository;
	
	public Commit(){
		
	}
	
	public Commit(String login, String message, String hash, String repository){
		this.login = login;
		this.message = message;
		this.hash = hash;
		this.repository = repository;
	}
	
	public Commit(String id, String login, String message, String hash, String repository){
		this.login = login;
		this.message = message;
		this.hash = hash;
		this.repository = repository;
		this.id = id;
	}
	
	
	public Commit(String _id, String _class, String login, String message, String hash, String repository){
		this._id = _id;
		this._class = _class;
		
		this.login = login;
		this.message = message;
		this.hash = hash;
		this.repository = repository;
		
	}
	
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}
	
	
		
}
