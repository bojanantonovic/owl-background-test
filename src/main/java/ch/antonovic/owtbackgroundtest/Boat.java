package ch.antonovic.owtbackgroundtest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Boat {

	@Id
	private Long id;
	private String name;
	private String description;

	public Boat() {
	}

	public Boat(final Long id, final String name, final String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
