package ch.antonovic.owlbackgroundtest.controller;

import ch.antonovic.owlbackgroundtest.persistance.Boat;
import ch.antonovic.owlbackgroundtest.service.BoatPersistenceService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final BoatPersistenceService boatPersistenceService;

	public MainController(final BoatPersistenceService boatPersistenceService) {
		this.boatPersistenceService = boatPersistenceService;
	}

	@PostConstruct
	public void init() {
		LOGGER.info("MainController initialized with BoatPersistenceService: {}", boatPersistenceService.getClass().getName());
	}

	// TODO: Diese Konfiguration (permitAll, CSRF/CORS offen) ist nur für lokale Demo-/Testzwecke geeignet
	// und muss vor einem Produktiveinsatz durch echte Authentifizierung/Autorisierung ersetzt werden.
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
	public ResponseEntity<Boat> addBoat(@Valid @RequestBody final JsonBoat jsonBoat) {
		LOGGER.info("Adding boat with name {} and description {}", jsonBoat.name(), jsonBoat.description());
		final var addedBoat = boatPersistenceService.addBoat(jsonBoat.name(), jsonBoat.description());
		final URI location = URI.create("/boats/" + addedBoat.getId());
		return ResponseEntity.created(location) // = Status 201
				.body(addedBoat);
	}

	@PutMapping(path = "/boats", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boat> updateBoat(@Valid @RequestBody final JsonBoatWithId jsonBoat) {
		LOGGER.info("Updating boat with id {}, name {} and description {}", jsonBoat.id(), jsonBoat.name(), jsonBoat.description());
		final var addedBoat = boatPersistenceService.updateBoat(jsonBoat.id(), jsonBoat.name(), jsonBoat.description());
		final URI location = URI.create("/boats/" + addedBoat.getId());
		return ResponseEntity.created(location) // = Status 201
				.body(addedBoat);
	}

	@DeleteMapping(path = "/boats/{id}")
	public ResponseEntity<Void> deleteBoat(final @PathVariable Long id) {
		boatPersistenceService.deleteBoat(id);
		return ResponseEntity.noContent().build(); // = Status 204
	}
}
