package com.pim.stars.game.imp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@EnableConfigurationProperties(GameProperties.class)
@ConfigurationProperties(prefix = "game")
@Getter
@Setter
public class GameProperties {

	private int startingYear = 2500;
}
