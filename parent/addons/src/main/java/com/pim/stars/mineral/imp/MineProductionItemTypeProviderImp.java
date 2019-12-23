package com.pim.stars.mineral.imp;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.pim.stars.mineral.api.MineProductionItemTypeProvider;
import com.pim.stars.mineral.imp.policies.MineProductionItemType;
import com.pim.stars.production.api.policies.ProductionItemType;
import com.pim.stars.production.api.policies.ProductionItemType.ProductionItemTypeFactory;

@Component
public class MineProductionItemTypeProviderImp implements ProductionItemTypeFactory, MineProductionItemTypeProvider {

	@Autowired
	private ApplicationContext applicationContext;

	private ProductionItemType mineProductionItemType;

	@Override
	public Collection<ProductionItemType> createProductionItemTypes() {
		return Arrays.asList(getMineProductionItemType());
	}

	@Override
	public ProductionItemType getMineProductionItemType() {
		if (mineProductionItemType == null) {
			mineProductionItemType = new MineProductionItemType();
			applicationContext.getAutowireCapableBeanFactory().autowireBean(mineProductionItemType);
		}
		return mineProductionItemType;
	}
}