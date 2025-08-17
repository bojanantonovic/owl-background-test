package ch.antonovic.owtbackgroundtest;

import java.util.List;

public interface BoatPersistenceService {

	List<Boat> getAllBoats();

	Boat addBoat(String name, String description);

	void deleteBoat(Long id);
}
