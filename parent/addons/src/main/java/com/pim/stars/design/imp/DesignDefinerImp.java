package com.pim.stars.design.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.design.api.Design;
import com.pim.stars.design.api.DesignDefiner;
import com.pim.stars.design.imp.persistence.DesignEntity;
import com.pim.stars.design.imp.persistence.DesignEntity.DesignEntityId;
import com.pim.stars.design.imp.persistence.DesignEntity.DesignEntityStatus;
import com.pim.stars.design.imp.persistence.DesignEntity.FilledGadgetSlot;
import com.pim.stars.design.imp.persistence.DesignRepository;
import com.pim.stars.gadget.api.Gadget;
import com.pim.stars.gadget.api.hull.GadgetSlot;
import com.pim.stars.gadget.api.hull.Hull;
import com.pim.stars.gadget.api.types.HullType;
import com.pim.stars.game.api.Game;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.race.api.Race;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
public class DesignDefinerImp implements DesignDefiner {

	@Autowired
	private IdCreator idCreator;
	@Autowired
	private DesignRepository designRepository;

	@Override
	public DesignBuilder start(final Game game, final Race race, final Hull hull) {
		return new DesignBuilderImp(game, race.getId(), hull);
	}

	private class DesignBuilderImp implements DesignBuilder {

		private final DesignEntity entity = new DesignEntity();
		private final Hull hull;

		public DesignBuilderImp(final Game game, final String ownerId, final Hull hull) {
			entity.setEntityId(new DesignEntityId(idCreator.createId(), game.getId(), game.getYear()));
			entity.setOwnerId(ownerId);
			entity.setHullId(hull.getId());
			this.hull = hull;
		}

		@Override
		public DesignBuilder fillSlot(final String slotId, final Gadget gadget, final int numberOfGadgets) {
			final GadgetSlot slot = hull.getGadgetSlots().stream()
					.filter(candidate -> candidate.getSlotId().equals(slotId)).findFirst()
					.orElseThrow(() -> incorrectSlotException(hull.getId(), slotId));
			return fillSlot(slot, gadget, numberOfGadgets);
		}

		private DesignBuilder fillSlot(final GadgetSlot slot, final Gadget gadget, final int numberOfGadgets) {
			final String slotId = slot.getSlotId();
			ensureSlotNotAlreadyFilled(slotId);
			ensureGadgetAllowedInSlot(slot, gadget, numberOfGadgets);

			entity.getFilledGadgetSlots().add(new FilledGadgetSlot(slotId, gadget.getId(), numberOfGadgets));

			return this;
		}

		private void ensureSlotNotAlreadyFilled(final String slotId) {
			final boolean slotIsAlreadyFilled = entity.getFilledGadgetSlots().stream()
					.anyMatch(candidate -> candidate.getSlotId().equals(slotId));
			if (slotIsAlreadyFilled) {
				throw new IllegalArgumentException("The slot with ID " + slotId + " has already been filled.");
			}
		}

		private void ensureGadgetAllowedInSlot(final GadgetSlot slot, final Gadget gadget, final int numberOfGadgets) {
			if (!slot.getAllowedGadgetTypes().stream()
					.anyMatch(type -> type.getId().equals(gadget.getGadgetType().getId()))) {
				throw new IllegalArgumentException("The slot with ID " + slot.getSlotId()
						+ " does not allow gadget with ID " + gadget.getId() + ", because type "
						+ gadget.getGadgetType() + " is not contained in the list of allowed gadget types: "
						+ slot.getAllowedGadgetTypes());
			} else if ((slot.getMinimumNumberOfGadgets() > numberOfGadgets)
					|| (slot.getMaximumNumberOfGadgets() < numberOfGadgets)) {
				throw new IllegalArgumentException("The slot with ID " + slot.getSlotId() + " does not allow to add "
						+ numberOfGadgets + ". It must be between " + slot.getMinimumNumberOfGadgets() + " and "
						+ slot.getMaximumNumberOfGadgets() + ".");
			}
		}

		@Override
		public Design build(final String designName) {
			entity.setName(designName);
			entity.setHullTypeId(hull.getHullType().getId());
			entity.setStatus(DesignEntityStatus.EDITABLE.getId());
			designRepository.save(entity);
			return new DesignImp(entity.getEntityId().getId(), entity.getName(), entity.getOwnerId(),
					hull.getHullType());
		}

		private IllegalArgumentException incorrectSlotException(final String hullId, final String slotId) {
			return new IllegalArgumentException("The hull with the ID " + hullId + " has no slot with ID " + slotId);
		}
	}

	@Getter
	@AllArgsConstructor
	private class DesignImp implements Design {

		private final String id;
		private final String name;
		private final String ownerId;
		private final HullType hullType;
	}
}
