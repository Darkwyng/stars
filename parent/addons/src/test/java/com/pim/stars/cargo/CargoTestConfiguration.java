package com.pim.stars.cargo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ CargoConfiguration.Provided.class })
public class CargoTestConfiguration implements CargoConfiguration.Required {

}