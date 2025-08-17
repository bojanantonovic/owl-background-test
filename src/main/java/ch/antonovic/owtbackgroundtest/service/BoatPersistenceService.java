package ch.antonovic.owtbackgroundtest.service;

import ch.antonovic.owtbackgroundtest.persistance.Boat;

import java.util.List;

public interface BoatPersistenceService {

	List<Boat> getAllBoats();

	Boat addBoat(String name, String description);

	Boat updateBoat(Long id, String name, String description);

	void deleteBoat(Long id);
}
