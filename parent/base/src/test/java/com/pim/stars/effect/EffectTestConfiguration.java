package com.pim.stars.effect;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.effect.api.EffectConfiguration;

@Configuration
@Import({ EffectConfiguration.Provided.class })
public class EffectTestConfiguration implements EffectConfiguration.Required {

}