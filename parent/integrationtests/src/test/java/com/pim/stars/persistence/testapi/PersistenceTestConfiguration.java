package com.pim.stars.persistence.testapi;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration // Required by @DataMongoTest
@DataMongoTest
public class PersistenceTestConfiguration {

}
