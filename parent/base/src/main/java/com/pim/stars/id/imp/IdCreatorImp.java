package com.pim.stars.id.imp;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.pim.stars.id.api.IdCreator;

@Component
public class IdCreatorImp implements IdCreator {

	private final Random random = new Random();

	@Override
	public String createId() {
		return Long.toString(random.nextLong()) + "-" + Long.toString(random.nextLong());
	}
}