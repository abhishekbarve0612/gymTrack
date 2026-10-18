package dev.gymTrack.domain;

import java.util.Objects;
import java.util.Set;

public record Exercise(String name, MuscleGroup primary, Set<MuscleGroup> secondary, Equipment equipment) {

    public  Exercise {
        Objects.requireNonNull(primary, "Primary muscle is required!");
        Objects.requireNonNull(equipment, "Equipment is required!");

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Exercise name is required!");
        }

        name = name.strip();
        secondary = Set.copyOf(secondary);

        if (secondary.contains(primary)) {
            throw  new IllegalArgumentException(primary + " cannot be both primary and secondary!");
        }
    }

    public  boolean isCompound() {
        return !secondary.isEmpty();
    }
}
