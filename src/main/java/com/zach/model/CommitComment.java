package com.zach.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "commitComments")
public class CommitComment {
	private String comment;
	private String commitHash;
	
	public CommitComment(){}

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
