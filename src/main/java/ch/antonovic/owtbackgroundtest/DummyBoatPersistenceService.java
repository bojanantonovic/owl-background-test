package ch.antonovic.owtbackgroundtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DummyBoatPersistenceService implements BoatPersistenceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DummyBoatPersistenceService.class);

	private long index = 0;
	private final Map<Long, Boat> boats = new HashMap<>();

	@Override
	public List<Boat> getAllBoats() {
		return new ArrayList<>(boats.values());
	}

	@Override
	public Boat addBoat(final String name, final String description) {
		final Boat boat = new Boat(index++, name, description);
		boat.setId(index++);
		boats.put(boat.getId(), boat);
		LOGGER.info("Current boats: {}", boats);

		return boat;
	}

	@Override
	public void deleteBoat(final Long id) {
		LOGGER.info("Deleting boat with id {}", id);
		boats.remove(id);
		LOGGER.info("Current boats: {}", boats);
	}
}
