package ch.antonovic.owlbackgroundtest.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatJpaRepository extends JpaRepository<Boat, Long> {
}
