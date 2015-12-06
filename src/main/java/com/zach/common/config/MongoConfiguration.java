package com.zach.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/***
 * @author Japheth Odonya
 * @When Dec 6, 2015 1:57:22 PM
 * */
@Configuration
@EnableMongoRepositories(basePackages="com.zach.repository")
public class MongoConfiguration extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return "zach";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		// TODO Auto-generated method stub
		return new MongoClient("127.0.0.1");
	}

}
