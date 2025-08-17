package ch.antonovic.owlbackgroundtest.service;

import ch.antonovic.owlbackgroundtest.persistance.Boat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BoatDataBasePersistenceServiceTest {

	@Autowired
	private BoatDataBasePersistenceService boatDataBasePersistenceService;

	@Test
	void addingAndDeletion() {
		// testee
		final Boat boatToAdd = new Boat(1L, "Test Boat", "This is a test boatToAdd");

		// pre-assert
		final int numberOfBoatsBeforeInsertion = boatDataBasePersistenceService.getAllBoats().size();
		final var addedBoat = boatDataBasePersistenceService.addBoat(boatToAdd.getName(), boatToAdd.getDescription());
		final int numberOfBoatsAfterInsertion = boatDataBasePersistenceService.getAllBoats().size();
		assertEquals(numberOfBoatsBeforeInsertion + 1, numberOfBoatsAfterInsertion, "Boat was not added to the database");
		//
		boatDataBasePersistenceService.deleteBoat(addedBoat.getId());
		final int numberOfBoatsAfterDeletion = boatDataBasePersistenceService.getAllBoats().size();
		assertEquals(numberOfBoatsBeforeInsertion, numberOfBoatsAfterDeletion, "Boat was not deleted from the database");
	}
}
