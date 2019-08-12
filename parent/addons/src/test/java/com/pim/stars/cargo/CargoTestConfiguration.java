package com.pim.stars.cargo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.api.CargoConfiguration;

@Configuration
@Import({ CargoConfiguration.Provided.class })
public class CargoTestConfiguration implements CargoConfiguration.Required {

}