package dev.gymTrack.domain;

import dev.gymTrack.domain.Equipment;
import dev.gymTrack.domain.Exercise;
import dev.gymTrack.domain.MuscleGroup;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExerciseTest {

    @Test
    void secondaryMusclesCannotBeChangedFromOutside() {
        Set<MuscleGroup> helpers = new HashSet<>(Set.of(MuscleGroup.TRICEPS));
        Exercise bench = new Exercise("Bench Press", MuscleGroup.CHEST, helpers, Equipment.BARBELL);

        helpers.add(MuscleGroup.CALVES);   // mutate the caller's set afterwards

        assertFalse(bench.secondary().contains(MuscleGroup.CALVES));
        assertThrows(UnsupportedOperationException.class, () -> bench.secondary().add(MuscleGroup.ABS));
    }

    @Test
    void recordsCompareByValue() {
        Exercise a = new Exercise("Squat", MuscleGroup.QUADS, Set.of(MuscleGroup.GLUTES), Equipment.BARBELL);
        Exercise b = new Exercise("  Squat ", MuscleGroup.QUADS, Set.of(MuscleGroup.GLUTES), Equipment.BARBELL);
        assertEquals(a, b);
    }

    @Test
    void primaryCannotAlsoBeSecondary() {
        assertThrows(IllegalArgumentException.class,
                () -> new Exercise("Odd Press", MuscleGroup.CHEST, Set.of(MuscleGroup.CHEST), Equipment.MACHINE));
    }

    @Test
    void muscleLookupIsLenient() {
        assertEquals(MuscleGroup.HAMSTRINGS, MuscleGroup.fromLabel("  hamstrings "));
        assertThrows(IllegalArgumentException.class, () -> MuscleGroup.fromLabel("forearms"));
    }
}