package com.zach.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.zach.common.config.MongoConfiguration;
import com.zach.model.Commit;

@Repository
/****
 * @author Japheth Odonya
 * @When Dec 15, 2015 12:15:35 AM
 * */
public class CommitRepositoryImp implements CommitRepositoryCustom {
	
	@Autowired
	MongoConfiguration mongoConfiguration;

	@Override
	public List<Commit> getCommits(String login) {
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
			// mongoTemplate.get
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Commit> listCommits = null;
		
		listCommits = (List<Commit>) mongoTemplate.find(new Query(Criteria
				.where("login").is(login)), Commit.class, "commits");
		
		return listCommits;
	}

	@Override
	public List<Commit> getCommitsGivenRepository(String repository) {
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
			// mongoTemplate.get
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Commit> listCommits = null;
		
		listCommits = (List<Commit>) mongoTemplate.find(new Query(Criteria
				.where("repository").is(repository)), Commit.class, "commits");
		
		return listCommits;
	}

	
	/****
	 * @author Japheth Odonya
	 * @When Dec 15, 2015 1:04:33 AM
	 * 
	 * Purpose: Count the Commits given a login
	 * 
	 * */
	@Override
	public Long countCommits(String login) {
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
			// mongoTemplate.get
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mongoTemplate.count(new Query(Criteria
				.where("login").is(login)), Commit.class, "commits");
		
	}

	/***
	 * @author Japheth Odonya
	 * @When Dec 15, 2015 1:03:44 AM
	 * 
	 * Purpose : Count the Commits given repository
	 * */
	@Override
	public Long countCommitsGivenRepository(String repository) {
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
			// mongoTemplate.get
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mongoTemplate.count(new Query(Criteria
				.where("repository").is(repository)), Commit.class, "commits");
		
	}

	@Override
	public List<Commit> getCommits(String login, Long limit) {
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
			// mongoTemplate.get
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Commit> listCommits = null;
		
		listCommits = (List<Commit>) mongoTemplate.find(new Query(Criteria
				.where("login").is(login)).limit(limit.intValue()), Commit.class, "commits");
		
		return listCommits;
	}

	@Override
	public List<Commit> getCommitsGivenRepository(String repository, Long limit) {
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
			// mongoTemplate.get
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Commit> listCommits = null;
		
		listCommits = (List<Commit>) mongoTemplate.find(new Query(Criteria
				.where("repository").is(repository)).limit(limit.intValue()), Commit.class, "commits");
		
		return listCommits;
	}
	
	
	public List<Commit> getCommits(Long limit) {
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
			// mongoTemplate.get
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Commit> listCommits = null;
		
		listCommits = (List<Commit>) mongoTemplate.find(new Query().limit(limit.intValue()), Commit.class, "commits");
		
		return listCommits;
	}

	@Override
	public List<Commit> getCommitsGivenRepository(Long limit) {
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = mongoConfiguration.mongoTemplate();
			// mongoTemplate.get
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Commit> listCommits = null;
		
		listCommits = (List<Commit>) mongoTemplate.find(new Query().limit(limit.intValue()), Commit.class, "commits");
		
		return listCommits;
	}


}
