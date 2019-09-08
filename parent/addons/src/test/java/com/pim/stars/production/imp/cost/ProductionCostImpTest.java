package com.pim.stars.production.imp.cost;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hamcrest.Matcher;
import org.hamcrest.number.IsCloseTo;
import org.junit.jupiter.api.Test;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.production.imp.cost.ProductionCostImp.ProductionCostBuilderImp;

public class ProductionCostImpTest {

	private final ProductionCostType typeOne = new ProductionCostType() {

		@Override
		public String getId() {
			return "1";
		}

		@Override
		public void deduct(final Game game, final Planet planet, final int amount) {

		}
	};

	private final ProductionCostType typeTwo = new ProductionCostType() {

		@Override
		public String getId() {
			return "2";
		}

		@Override
		public void deduct(final Game game, final Planet planet, final int amount) {

		}
	};

	private final ProductionCost zero = new ProductionCostBuilderImp().build();
	private final ProductionCost one_0 = new ProductionCostBuilderImp().add(typeOne, 0).build();
	private final ProductionCost one_17 = new ProductionCostBuilderImp().add(typeOne, 17).build();
	private final ProductionCost two_15 = new ProductionCostBuilderImp().add(typeTwo, 15).build();
	private final ProductionCost one_12_two_16 = new ProductionCostBuilderImp().add(typeOne, 12).add(typeTwo, 16)
			.build();

	private final ProductionCost one_35 = new ProductionCostBuilderImp().add(typeOne, 35).build();
	private final ProductionCost two_29 = new ProductionCostBuilderImp().add(typeTwo, 29).build();
	private final ProductionCost one_35_two_29 = new ProductionCostBuilderImp().add(typeOne, 35).add(typeTwo, 29)
			.build();

	@Test()
	public void testThatDivFailsForDivZero() {
		assertAll(() -> assertThrows(ArithmeticException.class, () -> zero.div(zero)),
				() -> assertThrows(ArithmeticException.class, () -> one_0.div(zero)),
				() -> assertThrows(ArithmeticException.class, () -> zero.div(one_0)),
				() -> assertThrows(ArithmeticException.class, () -> one_0.div(one_0)),
				() -> assertThrows(ArithmeticException.class, () -> one_17.div(one_0)),
				() -> assertThrows(ArithmeticException.class, () -> two_15.div(one_0)),
				() -> assertThrows(ArithmeticException.class, () -> one_12_two_16.div(zero)));
	}

	@Test()
	public void testThatDivReturnsZero() {
		assertAll(() -> assertThat("zero.div(one_17)", zero.div(one_17), is(0.0)), //
				() -> assertThat("one_17.div(two_15)", one_17.div(two_15), is(0.0)), //
				() -> assertThat("two_15.div(one_17)", two_15.div(one_17), is(0.0)), //
				() -> assertThat("one_12_two_16.div(one_17)", one_12_two_16.div(one_17), closeTo(12, 17)));
	}

	@Test()
	public void testThatXDivXReturnsOne() {
		assertAll(() -> assertThat(one_17.div(one_17), is(1.0)), //
				() -> assertThat(one_35_two_29.div(one_35), is(1.0)), //
				() -> assertThat(one_12_two_16.div(one_12_two_16), is(1.0)));
	}

	@Test()
	public void testThatDivReturnsPositiveValue() {
		assertAll(() -> assertThat(one_35.div(one_17), closeTo(35, 17)), //
				() -> assertThat(two_29.div(two_15), closeTo(29, 15)), //
				() -> assertThat(one_35_two_29.div(one_17), closeTo(35, 17)), //
				() -> assertThat(one_35_two_29.div(one_12_two_16), closeTo(29, 16)));
	}

	private Matcher<Double> closeTo(final int a, final int b) {
		return IsCloseTo.closeTo(a / (double) b, 0.00001);
	}
}