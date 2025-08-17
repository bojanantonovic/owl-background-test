package ch.antonovic.owtbackgroundtest.controller;

import ch.antonovic.owtbackgroundtest.persistance.Boat;
import ch.antonovic.owtbackgroundtest.service.BoatPersistenceService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MainController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private BoatPersistenceService boatPersistenceService;

	@PostConstruct
	public void postConstruct() {
		LOGGER.info("BoatPersistenceService: {}", boatPersistenceService.getClass().getName());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // alle Requests erlauben
				.csrf(AbstractHttpConfigurer::disable)  // CSRF-Schutz deaktivieren (für reine APIs oft nötig)
				.cors(cors -> {
				})                       // aktiviert CORS in Security
				.formLogin(AbstractHttpConfigurer::disable) // Login-Formular abschalten
				.httpBasic(AbstractHttpConfigurer::disable); // Basic-Auth abschalten

		return http.build();
	}

	@PostConstruct
	public void init() {
		LOGGER.info("MainController initialized!");
		boatPersistenceService.addBoat("Dummy Boat", "This is a dummy boat for testing purposes.");
	}

	@GetMapping("/")
	public String helloWorld() {
		return "Hello World!";
	}

	@GetMapping(path = "/boats", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Boat> getAllBoats() {
		final List<Boat> boats = boatPersistenceService.getAllBoats();
		LOGGER.info("Getting all boats: {}", boats);
		return boats;
	}

	@PostMapping(path = "/boats", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boat> addBoat(@RequestBody final JsonBoat jsonBoat) {
		LOGGER.info("Adding boat with name {} and description {}", jsonBoat.name(), jsonBoat.description());
		final var addedBoat = boatPersistenceService.addBoat(jsonBoat.name(), jsonBoat.description());
		final URI location = URI.create("/boats/" + addedBoat.getId());
		return ResponseEntity.created(location) // = Status 201
				.body(addedBoat);
	}

	@DeleteMapping(path = "/boats/{id}")
	public void deleteBoat(final @PathVariable Long id) {
		boatPersistenceService.deleteBoat(id);
	}
}
