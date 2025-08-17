package ch.antonovic.owtbackgroundtest.controller;

public record JsonBoat(String name, String description) {
/*
	public JsonBoat(Boat boat) {
		this(boat.getName(), boat.getDescription());
	}

	public Boat toBoat(Long id) {
		return new Boat(id, name, description);
	}

	@Override
	public String toString() {
		return "JsonBoat{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
	}*/
}
