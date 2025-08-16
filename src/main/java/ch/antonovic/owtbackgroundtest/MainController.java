package ch.antonovic.owtbackgroundtest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
	long index = 0;
	final Map<Long, Boat> boats = new HashMap<>();

	@PostMapping("/")
	public Boat addBoat(final String name, final String description) {
		final var boat = new Boat(index++, name, description);
		boats.put(boat.getId(), boat);
		return boat;
	}

	@DeleteMapping("/")
	public void deleteBoat(final Long id) {
		boats.remove(id);
	}
}
