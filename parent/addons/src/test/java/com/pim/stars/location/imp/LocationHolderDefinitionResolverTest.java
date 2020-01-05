package com.pim.stars.location.imp;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.pim.stars.location.imp.persistence.LocationHolderDefinitionResolver;

public class LocationHolderDefinitionResolverTest {

	private final LocationHolderDefinitionResolver testee = new LocationHolderDefinitionResolver();

	@Test
	public void testMissingDefinitionIsRejected() {
		assertThrows(IllegalStateException.class, () -> testee.unwrapLocationHolder(new Object(), null));
	}
}
