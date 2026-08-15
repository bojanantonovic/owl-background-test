package ch.antonovic.owlbackgroundtest.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JsonBoatWithId(@NotNull Long id, @NotBlank String name, @NotBlank String description) {
}
