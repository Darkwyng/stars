package com.pim.stars.turn;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ TurnConfiguration.Provided.class })
public class TurnTestConfiguration implements TurnConfiguration.Required {

}