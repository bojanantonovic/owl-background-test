package ch.antonovic.owlbackgroundtest.controller;

import jakarta.validation.constraints.NotBlank;

public record JsonBoat(@NotBlank String name, @NotBlank String description) {

}
