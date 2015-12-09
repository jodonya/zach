package com.zach.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/***
 * @author Japheth Odonya
 * @When Dec 8, 2015 2:39:30 AM
 * 
 * Purpose : Comments on a Commit
 * */
@Document(collection = "commitComments")
public class CommitComment {
	private String comment;
	private String commitHash;
	private Date created;
	private String email;
	
	public CommitComment(){}
	
	public CommitComment(String comment, String commitHash){
		this.comment = comment;
		this.commitHash = commitHash;
	}
	
	public CommitComment(String comment, String commitHash, String email, Date created){
		this.comment = comment;
		this.commitHash = commitHash;
		this.email = email;
		this.created = created;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommitHash() {
		return commitHash;
	}

	public void setCommitHash(String commitHash) {
		this.commitHash = commitHash;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
