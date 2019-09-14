open module com.pim.stars.addons {

	requires com.pim.stars.base;

	requires transitive spring.boot;

	exports com.pim.stars.cargo.api;
	exports com.pim.stars.colonization.api;
	exports com.pim.stars.mineral.api;
	exports com.pim.stars.planets.api;
	exports com.pim.stars.production.api;
	exports com.pim.stars.race.api;
	exports com.pim.stars.resource.api;

}