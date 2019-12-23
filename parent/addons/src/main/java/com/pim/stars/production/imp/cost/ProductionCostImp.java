package com.pim.stars.production.imp.cost;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.pim.stars.production.api.cost.ProductionCost;
import com.pim.stars.production.api.policies.ProductionCostType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public class ProductionCostImp implements ProductionCost {

	private final Collection<ProductionCostItem> items;

	public ProductionCostImp(final Collection<ProductionCostItem> items) {
		this.items = items;
	}

	@Override
	public Collection<ProductionCostItem> getItems() {
		return items;
	}

	@Override
	public ProductionCost add(final ProductionCost other) {
		final ProductionCostBuilder builder = new ProductionCostBuilderImp();
		getItems().stream().forEach(item -> builder.add(item.getType(), item.getAmount()));
		other.getItems().stream().forEach(item -> builder.add(item.getType(), item.getAmount()));

		return builder.build();
	}

	@Override
	public ProductionCost subtract(final ProductionCost other) {
		final ProductionCostBuilder builder = new ProductionCostBuilderImp();
		getItems().stream().forEach(item -> builder.add(item.getType(), item.getAmount()));

		final int negativeOne = -1;
		other.getItems().stream().forEach(item -> builder.add(item.getType(), negativeOne * item.getAmount()));

		return builder.build();
	}

	@Override
	public ProductionCost multiply(final double factor, final Function<Double, Integer> roundingFunction) {
		final ProductionCostBuilder builder = new ProductionCostBuilderImp();
		getItems().stream().forEach(item -> {
			final int product = roundingFunction.apply(item.getAmount() * factor);
			builder.add(item.getType(), product);
		});

		return builder.build();
	}

	@Override
	public ProductionCost multiply(final int factor) {
		return multiply(factor, input -> input.intValue());
	}

	@Override
	public double div(final ProductionCost other) {
		if (other.isZero()) {
			throw new ArithmeticException();

		} else if (isZero()) {
			return 0;

		} else {
			double result = Double.MAX_VALUE;
			for (final ProductionCostItem otherItem : other.getItems()) {
				if (otherItem.getAmount() > 0) {
					final Optional<ProductionCostItem> optionalItem = items.stream()
							.filter(candidate -> candidate.getType().equals(otherItem.getType())).findAny();
					if (optionalItem.isPresent()) {
						final ProductionCostItem item = optionalItem.get();
						result = Math.min(result, item.getAmount() / (double) otherItem.getAmount());
					} else {
						return 0;
					}
				}
			}
			return result;
		}
	}

	@Override
	public boolean isZero() {
		return items.isEmpty() || !items.stream().anyMatch(item -> item.getAmount() > 0);
	}

	@Override
	public String toString() {
		return "[" + items.stream().map(Object::toString).collect(Collectors.joining(",")) + "]";
	}

	@Getter
	@AllArgsConstructor
	@ToString
	public static class ProductionCostItemImp implements ProductionCostItem {

		private final ProductionCostType type;
		private final int amount;
	}

	public static class ProductionCostBuilderImp implements ProductionCostBuilder {

		private final Map<ProductionCostType, Integer> map = new HashMap<>();

		@Override
		public ProductionCostBuilder add(final ProductionCostType type, final int amount) {
			final Integer oldAmount = map.get(type);

			final Integer newAmount;
			if (oldAmount == null) {
				newAmount = amount;
			} else {
				newAmount = oldAmount + amount;
			}
			map.put(type, newAmount);

			return this;
		}

		@Override
		public ProductionCost build() {
			final List<ProductionCostItem> items = map.entrySet().stream().map(entry -> {
				if (entry.getValue() < 0) {
					throw new IllegalStateException(
							entry.getKey() + " must not be negative, but it is " + entry.getValue());
				}

				return new ProductionCostItemImp(entry.getKey(), entry.getValue());
			}).collect(Collectors.toList());

			return new ProductionCostImp(items);
		}
	}
}