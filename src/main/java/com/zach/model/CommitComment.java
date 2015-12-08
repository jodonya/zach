package com.zach.model;

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
	
	public CommitComment(){}
	
	public CommitComment(String comment, String commitHash){
		this.comment = comment;
		this.commitHash = commitHash;
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
	

}
