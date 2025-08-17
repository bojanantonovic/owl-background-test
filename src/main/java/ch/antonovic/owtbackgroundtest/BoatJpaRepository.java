package ch.antonovic.owtbackgroundtest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatJpaRepository extends JpaRepository<Boat, Long> {
	// This interface extends JpaRepository, which provides methods for CRUD operations.
	// No additional methods are needed here as JpaRepository already provides the necessary functionality.{
}
