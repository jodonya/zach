package com.zach.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zach.model.Commit;

@Repository
public interface CommitRepository extends MongoRepository<Commit, String> {

}
