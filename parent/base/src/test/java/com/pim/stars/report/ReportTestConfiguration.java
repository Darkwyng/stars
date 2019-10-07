package com.pim.stars.report;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ ReportConfiguration.Provided.class })
@EnableAutoConfiguration // Required by @DataMongoTest
@DataMongoTest
public class ReportTestConfiguration implements ReportConfiguration.Required {

}