package com.zach.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zach.model.Commit;
@Repository
public interface CommitRepositoryCustom {
	
	public List<Commit> getCommits(String login);
	public List<Commit> getCommitsGivenRepository(String repository);
	
	public Long countCommits(String login);
	public Long countCommitsGivenRepository(String repository);
	
	public List<Commit> getCommits(String login, Long limit);
	public List<Commit> getCommitsGivenRepository(String repository, Long limit);
	
	public List<Commit> getCommits(Long limit);
	public List<Commit> getCommitsGivenRepository(Long limit);



}
