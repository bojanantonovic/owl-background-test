package ch.antonovic.owtbackgroundtest;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MainController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private BoatPersistenceService boatPersistenceService;

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
	public Boat addBoat(@RequestBody final JsonBoat jsonBoat) {
		LOGGER.info("Adding boat with name {} and description {}", jsonBoat.name(), jsonBoat.description());
		return boatPersistenceService.addBoat(jsonBoat.name(), jsonBoat.description());
	}

	@DeleteMapping(path = "/boats/{id}")
	public void deleteBoat(final @PathVariable Long id) {
		boatPersistenceService.deleteBoat(id);
	}
}
