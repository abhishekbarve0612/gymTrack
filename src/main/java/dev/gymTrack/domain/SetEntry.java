package dev.gymTrack.domain;

import java.util.Objects;

public record SetEntry(int reps, Weight weight) {

    public SetEntry {
        Objects.requireNonNull(weight, "Weight is required!");

        if (reps < 1) {
            throw new IllegalArgumentException("A set needs at least one rep, got " + reps);
        }
    }

    public Weight volume() {
        return weight.times(reps);
    }
}
