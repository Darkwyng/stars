package com.pim.stars.mineral.testapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.mineral.MineralConfiguration;

@Configuration
@Import({ MineralConfiguration.Complete.class })
public class MineralTestApiConfiguration {

	@Bean
	public MineralTestDataAccessor mineralTestDataAccessor() {
		return new MineralTestDataAccessor();
	}
}
