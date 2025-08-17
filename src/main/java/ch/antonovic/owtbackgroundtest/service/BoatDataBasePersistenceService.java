package ch.antonovic.owtbackgroundtest.service;

import ch.antonovic.owtbackgroundtest.persistance.Boat;
import ch.antonovic.owtbackgroundtest.persistance.BoatJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class BoatDataBasePersistenceService implements BoatPersistenceService {
	@Autowired
	private BoatJpaRepository boatJpaRepository;

	@Override
	public List<Boat> getAllBoats() {
		return boatJpaRepository.findAll();
	}

	@Override
	public Boat addBoat(final String name, final String description) {
		final Boat boat = new Boat(null, name, description);
		return boatJpaRepository.save(boat);
	}

	@Override
	public void deleteBoat(final Long id) {
		boatJpaRepository.deleteById(id);
	}
}
