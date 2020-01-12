package com.pim.stars.location.api.extensions;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.location.api.extensions.GameInitializationDataUniverseSize.UniverseSize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
public class GameInitializationDataUniverseSize implements DataExtensionPolicy<GameInitializationData, UniverseSize> {

	@Override
	public Class<GameInitializationData> getEntityClass() {
		return GameInitializationData.class;
	}

	@Override
	public Optional<? extends UniverseSize> getDefaultValue() {
		return Optional.of(new UniverseSize(600, 400));
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UniverseSize {

		private int maxX;
		private int maxY;
	}
}