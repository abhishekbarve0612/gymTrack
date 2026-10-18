package dev.gymTrack.domain;

import java.time.LocalDate;
import java.util.*;

public final class WorkoutSession {

    private final UUID id;
    private  final LocalDate date;
    private final Map<Exercise, List<SetEntry>> setsByExercise = new LinkedHashMap<>();

    public WorkoutSession(LocalDate date) {
        this(UUID.randomUUID(), date);
    }

    public  WorkoutSession(UUID id, LocalDate date) {
        this.id = Objects.requireNonNull(id);
        this.date = Objects.requireNonNull(date);
    }

    public void log(Exercise exercise, SetEntry set) {
        setsByExercise.computeIfAbsent(exercise, e -> new ArrayList<>()).add(set);
    }

    public  UUID id() {
        return id;
    }

    public LocalDate date() {
        return date;
    }

    public Map<Exercise, List<SetEntry>> sets() {
        Map<Exercise, List<SetEntry>> snapshot = new LinkedHashMap<>();
        setsByExercise.forEach(
                ((exercise, setEntries) -> snapshot.put(
                        exercise, List.copyOf(setEntries))));

        return Collections.unmodifiableMap(snapshot);
    }

    public int totalSets() {
        return setsByExercise.values().stream().mapToInt(List::size).sum();
    }

    public Weight totalVolume() {
        return setsByExercise.values().stream()
                .flatMap(List::stream)
                .map(SetEntry::volume)
                .reduce(Weight.ZERO, Weight::plus);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WorkoutSession other && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "WorkoutSession[" + date + ", " + totalSets() + " sets, " + totalVolume() + "]";
    }
}
