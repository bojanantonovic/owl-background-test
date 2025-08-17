package ch.antonovic.owlbackgroundtest.controller;

import ch.antonovic.owlbackgroundtest.persistance.Boat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class MainControllerTest {

	@LocalServerPort
	private int port;
	private final TestRestTemplate testRestTemplate = new TestRestTemplate();
	private String url;

	@BeforeEach()
	void setUp() {
		url = "http://localhost:" + port + "/boats";
	}

	@Test
	void addingAndDeletion() {
		final Boat boatToCreate = new Boat(null, "Sea Breeze", "Fast");

		final int numberOfBoatsBeforeInsertion = getBoats().getBody().length;

		final var createdBoatResponse = getCreatedBoat(boatToCreate);

		final int numberOfBoatsAfterInsertion = getBoats().getBody().length;
		assertEquals(numberOfBoatsBeforeInsertion + 1, numberOfBoatsAfterInsertion);

		// test deleting
		testRestTemplate.delete("http://localhost:" + port + "/boats/{id}", createdBoatResponse.getBody().getId());

		final int numberOfBoatsAfterDeletion = getBoats().getBody().length;
		assertEquals(numberOfBoatsBeforeInsertion, numberOfBoatsAfterDeletion, "Boat was not deleted from the database");
	}

	@Test
	void addingAndUpdating() {
		final Boat boatToCreate = new Boat(null, "Sea Breeze", "Fast");

		final ResponseEntity<Boat> createdBoatResponse = createBoat(boatToCreate);

		// test put
		final var newDescription = "New Description";
		final var createdBoat = createdBoatResponse.getBody();
		createdBoat.setDescription(newDescription);
		testRestTemplate.put("http://localhost:" + port + "/boats", createdBoatResponse, createdBoat.getId());

		// read the updated boat
		final var optionalBoat = Arrays.stream(getBoats().getBody()) //
				.filter(boat -> boat.getId().equals(createdBoat.getId()))  //
				.findAny();
		assertNotNull(optionalBoat);
		assertTrue(optionalBoat.isPresent(), "Boat was not found after update");
		assertEquals(newDescription, optionalBoat.get().getDescription(), "Boat description was not updated");
	}

	private ResponseEntity<Boat[]> getBoats() {
		final ResponseEntity<Boat[]> boatsResponse = testRestTemplate.getForEntity(url, Boat[].class);

		// pre-asserts
		assertEquals(HttpStatus.OK, boatsResponse.getStatusCode());
		assertNotNull(boatsResponse.getBody());

		return boatsResponse;
	}

	private ResponseEntity<Boat> getCreatedBoat(final Boat boatToCreate) {
		final ResponseEntity<Boat> createdBoat = createBoat(boatToCreate);

		// pre-asserts
		assertEquals(HttpStatus.CREATED, createdBoat.getStatusCode());
		assertNotNull(createdBoat.getBody());
		return createdBoat;
	}

	private ResponseEntity<Boat> createBoat(final Boat boatToCreate) {
		return testRestTemplate.postForEntity(url, boatToCreate, Boat.class);
	}
}

