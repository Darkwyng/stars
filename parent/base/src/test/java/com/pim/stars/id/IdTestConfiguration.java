package com.pim.stars.id;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ IdConfiguration.Provided.class })
public class IdTestConfiguration implements IdConfiguration.Required {

}