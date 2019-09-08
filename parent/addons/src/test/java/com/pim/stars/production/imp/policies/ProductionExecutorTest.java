package com.pim.stars.production.imp.policies;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.hamcrest.number.OrderingComparison;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.api.cost.ProductionCost.ProductionCostBuilder;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.production.imp.ProductionQueue;
import com.pim.stars.production.imp.ProductionQueueEntry;
import com.pim.stars.production.imp.cost.ProductionCostImp.ProductionCostBuilderImp;
import com.pim.stars.production.imp.effects.ProductionExecutor;
import com.pim.stars.production.imp.effects.ProductionResultBuilder;
import com.pim.stars.production.imp.effects.ProductionResultBuilder.PlanetProductionResult;
import com.pim.stars.production.imp.effects.ProductionResultBuilder.ProducedItem;

public class ProductionExecutorTest {

	@Mock
	private Game game;
	@Mock
	private Planet planet;

	private final ProductionCostTypeForTest ONE = new ProductionCostTypeForTest("1");
	private final ProductionCostTypeForTest TWO = new ProductionCostTypeForTest("2");

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testEmptyQueue() {
		final ProductionQueue queue = new ProductionQueue();

		final PlanetProductionResult result = callTestee(queue, createCost(1, 0));
		assertThat(result.getProducedItems(), empty());
		assertThat(result.getRemainingResources().getItems(), hasSize(1));

		assertProductionCost(result.getRemainingResources(), 1, 0);
		assertPayment(0, 0);
		assertThat(queue.isEmpty(), is(true));
	}

	@Test
	public void testOneItemIsInvestedIn() {
		final ProductionQueue queue = new ProductionQueue();
		final ProductionItemTypeForTest itemType = new ProductionItemTypeForTest(createCost(10, 0));
		addToQueue(queue, itemType, 1);

		final PlanetProductionResult result = callTestee(queue, createCost(9, 0));
		assertThat(result.getProducedItems(), empty());
		assertProductionCost(result.getRemainingResources(), 0, 0);

		assertThat(queue, not(emptyIterable()));
		final ProductionQueueEntry entry = queue.iterator().next();
		assertProductionCost(entry.getInvestedCost(), 9, 0);

		assertPayment(9, 0);
		assertThat(queue.isEmpty(), is(false));
	}

	@Test
	public void testOneItemIsBuilt() {
		final ProductionQueue queue = new ProductionQueue();
		final ProductionItemTypeForTest itemType = new ProductionItemTypeForTest(createCost(10, 0));
		addToQueue(queue, itemType, 1);

		final PlanetProductionResult result = callTestee(queue, createCost(10, 0));
		result.getRemainingResources().getItems().forEach(remaining -> assertThat(remaining.getAmount(), is(0)));

		assertThat(result.getProducedItems(), hasSize(1));
		final ProducedItem producedItem = result.getProducedItems().iterator().next();
		assertThat(producedItem.getItemType(), is(itemType));
		assertThat(producedItem.getNumberOfItems(), is(1));
		assertThat(itemType.getProducedItems(), hasItems(1));

		assertThat(queue, emptyIterable());

		assertPayment(10, 0);
	}

	@Test
	public void testItemsOfOneEntryAreBuiltWithInvestment() {
		final ProductionQueue queue = new ProductionQueue();
		final ProductionItemTypeForTest itemType = new ProductionItemTypeForTest(createCost(10, 0));
		addToQueue(queue, itemType, 5);

		final PlanetProductionResult result = callTestee(queue, createCost(41, 2));
		assertProductionCost(result.getRemainingResources(), 0, 2);

		assertThat(result.getProducedItems(), hasSize(1));
		final ProducedItem producedItem = result.getProducedItems().iterator().next();
		assertThat(producedItem.getItemType(), is(itemType));
		assertThat(producedItem.getNumberOfItems(), is(4));
		assertThat(itemType.getProducedItems(), hasItems(4));

		assertThat(queue, not(emptyIterable()));
		final ProductionQueueEntry entry = queue.iterator().next();
		assertProductionCost(entry.getInvestedCost(), 1, 0);
		assertThat(entry.getNumberOfItemsToBuild(), is(1));

		assertPayment(41, 0);
	}

	@Test
	public void testMultipleItemsWithInvestmentForMultiple() {
		final ProductionQueue queue = new ProductionQueue();
		final ProductionItemTypeForTest firstItemType = new ProductionItemTypeForTest(createCost(10, 1));
		final ProductionItemTypeForTest secondItemType = new ProductionItemTypeForTest(createCost(1, 1));
		final ProductionItemTypeForTest thirdItemType = new ProductionItemTypeForTest(createCost(0, 1));
		addToQueue(queue, firstItemType, 5);
		addToQueue(queue, secondItemType, 1);
		addToQueue(queue, thirdItemType, 1);

		final PlanetProductionResult result = callTestee(queue, createCost(39, 5));
		// Should build 3 firsts for 30/3 and invest 9/0
		// Should not build or invest in seconds (because 0/2 is left)
		// Should finish third using 0/1
		// Remaining resources are 0/1

		assertProductionCost(result.getRemainingResources(), 0, 1);
		assertPayment(39, 4);

		// Produced:
		assertThat(result.getProducedItems(), hasSize(2));
		final ProducedItem firstProducedItem = result.getProducedItems().get(0);
		assertThat(firstProducedItem.getItemType(), is(firstItemType));
		assertThat(firstProducedItem.getNumberOfItems(), is(3));
		assertThat(firstItemType.getProducedItems(), hasItems(3));

		final ProducedItem secondProducedItem = result.getProducedItems().get(1);
		assertThat(secondProducedItem.getItemType(), is(thirdItemType));
		assertThat(secondProducedItem.getNumberOfItems(), is(1));
		assertThat(thirdItemType.getProducedItems(), hasItems(1));

		// Queue:
		assertThat(queue, iterableWithSize(2));
		final Iterator<ProductionQueueEntry> iterator = queue.iterator();
		final ProductionQueueEntry firstQueueItem = iterator.next();
		assertThat(firstQueueItem.getType(), is(firstItemType));
		assertThat(firstQueueItem.getNumberOfItemsToBuild(), is(2));
		assertProductionCost(firstQueueItem.getInvestedCost(), 9, 0);

		final ProductionQueueEntry secondQueueItem = iterator.next();
		assertThat(secondQueueItem.getType(), is(secondItemType));
		assertThat(secondQueueItem.getNumberOfItemsToBuild(), is(1));
		assertProductionCost(secondQueueItem.getInvestedCost(), 0, 0);
	}

	private void addToQueue(final ProductionQueue queue, final ProductionItemTypeForTest typeToBuild,
			final int numberOfItemsToBuild) {
		final ProductionQueueEntry entry = new ProductionQueueEntry(typeToBuild);
		entry.setNumberOfItemsToBuild(numberOfItemsToBuild);
		queue.addEntry(entry);
	}

	private ProductionCost createCost(final int costForOne, final int costForTwo) {
		final ProductionCostBuilder builder = new ProductionCostBuilderImp();
		if (costForOne > 0) {
			builder.add(ONE, costForOne);
		}
		if (costForTwo > 0) {
			builder.add(TWO, costForTwo);
		}
		return builder.build();
	}

	private PlanetProductionResult callTestee(final ProductionQueue queue, final ProductionCost initialResources) {
		final ProductionResultBuilder resultBuilder = new ProductionResultBuilder();
		final ProductionExecutor testee = new ProductionExecutor(game, planet, queue, initialResources);

		testee.execute(resultBuilder);
		final Collection<PlanetProductionResult> resultCollection = resultBuilder.finish();
		assertThat(resultCollection, hasSize(1));
		final PlanetProductionResult result = resultCollection.iterator().next();
		assertThat(result.getPlanet(), is(planet));

		return result;
	}

	private void assertProductionCost(final ProductionCost costToCheck, final int expectedAmountOne,
			final int expectedAmountTwo) {
		final int minSize = (expectedAmountOne == 0 ? 0 : 1) + (expectedAmountTwo == 0 ? 0 : 1);
		assertThat(costToCheck.getItems(), hasSize(OrderingComparison.greaterThanOrEqualTo(minSize)));

		costToCheck.getItems().stream().forEach(item -> {
			if (item.getType() == ONE) {
				assertThat(item.getAmount(), is(expectedAmountOne));
			} else if (item.getType() == TWO) {
				assertThat(item.getAmount(), is(expectedAmountTwo));
			} else {
				fail(item.getType().toString());
			}
		});
	}

	private void assertPayment(final int expectedProductionOne, final int expectedProductionTwo) {
		assertThat(ONE.getDeductedAmounts().stream().mapToInt(Integer::intValue).sum(), is(expectedProductionOne));
		assertThat(TWO.getDeductedAmounts().stream().mapToInt(Integer::intValue).sum(), is(expectedProductionTwo));
	}

	private class ProductionItemTypeForTest implements ProductionItemType {

		private final ProductionCost cost;
		private final Collection<Integer> producedItems = new ArrayList<>();

		public ProductionItemTypeForTest(final ProductionCost cost) {
			this.cost = cost;
		}

		@Override
		public ProductionCost getCostPerItem(Game game, Planet planet, ProductionCostBuilder builder) {
			return cost;
		}

		@Override
		public void produce(Game game, Planet planet, final int numberOfItems) {
			producedItems.add(numberOfItems);
		}

		public Collection<Integer> getProducedItems() {
			return producedItems;
		}
	}

	private class ProductionCostTypeForTest implements ProductionCostType {

		private final Collection<Integer> deductedAmounts = new ArrayList<>();
		private final String id;

		public ProductionCostTypeForTest(final String id) {
			this.id = id;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public void deduct(final Game game, final Planet planet, final int amount) {
			assertThat(game, not(nullValue()));
			assertThat(planet, not(nullValue()));

			deductedAmounts.add(amount);
		}

		public Collection<Integer> getDeductedAmounts() {
			return deductedAmounts;
		}
	}
}