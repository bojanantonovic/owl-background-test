package ch.antonovic.owlbackgroundtest.service;

import ch.antonovic.owlbackgroundtest.persistance.Boat;
import ch.antonovic.owlbackgroundtest.persistance.BoatJpaRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class BoatDataBasePersistenceService implements BoatPersistenceService {
	private final BoatJpaRepository boatJpaRepository;

	public BoatDataBasePersistenceService(final BoatJpaRepository boatJpaRepository) {
		this.boatJpaRepository = boatJpaRepository;
	}

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
	public Boat updateBoat(final Long id, final String name, final String description) {
		final Boat boat = new Boat(id, name, description);
		return boatJpaRepository.save(boat);
	}

	@Override
	public void deleteBoat(final Long id) {
		boatJpaRepository.deleteById(id);
	}
}
