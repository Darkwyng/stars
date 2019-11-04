package com.pim.stars.dataextension;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ DataExtensionConfiguration.Provided.class })
public class DataExtensionTestConfiguration implements DataExtensionConfiguration.Required {

}