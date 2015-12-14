package com.zach.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zach.model.Commit;

/***
 * @author Japheth Odonya
 * @When Dec 14, 2015 9:32:38 AM
 * 
 * Repository for Commits
 * */
@Repository
public interface CommitRepository extends MongoRepository<Commit, String> {

}
