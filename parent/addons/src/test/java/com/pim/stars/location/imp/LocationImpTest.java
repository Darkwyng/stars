package com.pim.stars.location.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.pim.stars.location.api.Location;

public class LocationImpTest {

	@ParameterizedTest
	@CsvSource({ "47, 11, 0", "47, 12, 1", "46, 11, 1", "46, 10, 2" })
	public void testDistanceCalculation(final int x, final int y, final int expected) {
		final Location one = LocationImp.Builder.builder(47, 11).build();
		final Location two = LocationImp.Builder.builder(x, y).build();
		assertThat(one.getDistanceForComparison(two), is(expected));
		assertThat(two.getDistanceForComparison(one), is(expected));
		assertThat(one.getDistance(two), is(Math.sqrt(expected)));
		assertThat(two.getDistance(one), is(Math.sqrt(expected)));
	}

	@Test
	public void testIsSameLocationHandlesNull() {
		final Location one = LocationImp.Builder.builder(47, 11).build();
		assertThat(one.isSameLocation(null), is(false));
	}

	@ParameterizedTest
	@CsvSource({ "47, 11, true", "47, 12, false", "46, 11, false" })
	public void testIsSameLocation(final int x, final int y, final boolean expected) {
		final Location one = LocationImp.Builder.builder(47, 11).build();
		final Location two = LocationImp.Builder.builder(x, y).build();
		assertThat(one.isSameLocation(two), is(expected));
	}

}
