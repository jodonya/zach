package com.zach.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Japheth Odonya
 * @When Dec 7, 2015 11:23:56 AM
 * 
 * Purpose : The Commit File -  for adding a list of files to the Commit
 * */
@Document(collection = "commitFiles")
public class CommitFile {
	private String id;
	private String fileName;
	private String status;
	private String patch; // the change
	
	
	public CommitFile(){}
	
	public CommitFile(String fileName, String status, String patch){
		this.fileName = fileName;
		this.status = status;
		this.patch = patch;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getPatch() {
		return patch;
	}


	public void setPatch(String patch) {
		this.patch = patch;
	}
	
}
