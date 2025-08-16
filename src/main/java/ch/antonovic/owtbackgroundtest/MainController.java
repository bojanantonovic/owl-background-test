package ch.antonovic.owtbackgroundtest;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class MainController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	long index = 0;
	final Map<Long, Boat> boats = new HashMap<>();

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
		final Boat dummyBoat = new Boat(index++, "Dummy Boat", "This is a dummy boat for testing purposes.");
		boats.put(dummyBoat.getId(), dummyBoat);
	}

	@GetMapping("/")
	public String helloWorld() {
		return "Hello World!";
	}

	@GetMapping(path = "/boats", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Boat> getAllBoats() {
		LOGGER.info("Getting all boats: {}", boats.values());
		return new ArrayList<>(boats.values());
	}

	@PostMapping(path = "/boats", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Boat addBoat(@RequestBody final Boat boat) {
		LOGGER.info("Adding boat with name {} and description {}", boat.getName(), boat.getDescription());
		boat.setId(index++);
		boats.put(boat.getId(), boat);
		LOGGER.info("Current boats: {}", boats);
		return boat;
	}

	@DeleteMapping(path = "/boats/{id}")
	public void deleteBoat(final @PathVariable  Long id) {
		LOGGER.info("Deleting boat with id {}", id);
		boats.remove(id);
		LOGGER.info("Current boats: {}", boats);
	}
}
