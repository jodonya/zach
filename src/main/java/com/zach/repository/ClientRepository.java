package com.zach.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zach.model.AppClient;

/***
 * @author Japheth Odonya
 * @When Dec 14, 2015 9:32:21 AM
 * 
 * Purpose : Repository for Manipulating Client
 * */
@Repository
public interface ClientRepository extends MongoRepository<AppClient, String> {

}
