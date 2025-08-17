package ch.antonovic.owtbackgroundtest.controller;

import ch.antonovic.owtbackgroundtest.persistance.Boat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class MainControllerTest {

	@LocalServerPort
	private int port;
	private final TestRestTemplate testRestTemplate = new TestRestTemplate();

	private String url(final String path) {
		return "http://localhost:" + port + path;
	}

	@Test
	void addingAndDeletion() {
		final Boat boatToCreate = new Boat(null, "Sea Breeze", "Fast");

		// pre-assert
		final int numberOfBoatsBeforeInsertion = testRestTemplate.getForObject(url("/boats"), Boat[].class).length;

		// add
		final ResponseEntity<Boat[]> boatsResponse = testRestTemplate.getForEntity(url("/boats"), Boat[].class);
		assertEquals(HttpStatus.OK, boatsResponse.getStatusCode());
		assertNotNull(boatsResponse.getBody());

		final ResponseEntity<Boat> createdBoat = testRestTemplate.postForEntity(url("/boats"), boatToCreate, Boat.class);
		assertEquals(HttpStatus.CREATED, createdBoat.getStatusCode());
		assertNotNull(createdBoat.getBody());

		final int numberOfBoatsAfterInsertion = testRestTemplate.getForObject(url("/boats"), Boat[].class).length;
		assertEquals(numberOfBoatsBeforeInsertion + 1, numberOfBoatsAfterInsertion);

		// test deleting
		testRestTemplate.delete("http://localhost:" + port + "/boats/{id}", createdBoat.getBody().getId());
	}
}

