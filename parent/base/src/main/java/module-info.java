open module com.pim.stars.base {

	requires transitive spring.beans;
	requires transitive spring.core;
	requires transitive spring.context;

	exports com.pim.stars.dataextension.api;
	exports com.pim.stars.dataextension.api.policies;
	exports com.pim.stars.effect.api;
	exports com.pim.stars.effect.api.policies;
	exports com.pim.stars.game.api;
	exports com.pim.stars.game.api.effects;
	exports com.pim.stars.id.api;
	exports com.pim.stars.id.api.extensions;
	exports com.pim.stars.turn.api;
	exports com.pim.stars.turn.api.policies;
	exports com.pim.stars.turn.api.policies.builder;
}