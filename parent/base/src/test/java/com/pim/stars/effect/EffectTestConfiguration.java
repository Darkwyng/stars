package com.pim.stars.effect;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ EffectConfiguration.Provided.class })
public class EffectTestConfiguration implements EffectConfiguration.Required {

}