package com.pim.stars.report.imp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(ReportProperties.class)
@ConfigurationProperties(prefix = "report")
public class ReportProperties {

}
