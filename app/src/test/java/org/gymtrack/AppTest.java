package org.gymtrack;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import org.gymtrack.model.WorkoutEntry;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void testWorkoutEntryGettersAndSetters() {
        WorkoutEntry entry = new WorkoutEntry();
        entry.setExercise("Bicep Curl");
        entry.setMuscleGroup("Arms");
        entry.setSets(3);
        entry.setReps(12);
        entry.setWeightKg(15.0);
        entry.setDate("2026-05-21");
        entry.setCompleted(true);

        assertEquals("Bicep Curl", entry.getExercise());
        assertEquals("Arms", entry.getMuscleGroup());
        assertEquals(3, entry.getSets());
        assertEquals(12, entry.getReps());
        assertEquals(15.0, entry.getWeightKg());
        assertEquals("2026-05-21", entry.getDate());
        assertTrue(entry.isCompleted());
    }

    @Test
    void testWorkoutEntryVolumeCalculation() {
        WorkoutEntry entry = new WorkoutEntry();
        entry.setSets(4);
        entry.setReps(10);
        entry.setWeightKg(50.0);

        assertEquals(2000.0, entry.getVolume());
    }

    @Test
    void testReadLogSuccessful() throws IOException {
        List<WorkoutEntry> log = App.readLog("workout_log.json");
        assertNotNull(log);
        assertFalse(log.isEmpty());
        assertEquals("Bench Press", log.get(0).getExercise());
    }
}

