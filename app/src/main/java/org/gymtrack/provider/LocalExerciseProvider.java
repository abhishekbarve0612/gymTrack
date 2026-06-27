package org.gymtrack.provider;

import org.gymtrack.model.WgerCategory;
import org.gymtrack.model.WgerExerciseInfo;
import org.gymtrack.model.WorkoutEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalExerciseProvider implements  WorkoutDataProvider {

    private final Map<String, WgerExerciseInfo> exercises = new HashMap<>();

    public LocalExerciseProvider(List<WorkoutEntry> log) {
        for (WorkoutEntry entry: log) {
            if (exercises.containsKey(entry.getExercise())) continue;

            WgerExerciseInfo info = new WgerExerciseInfo();
            info.setName(entry.getExercise());
            WgerCategory cat = new WgerCategory();
            cat.setName(entry.getMuscleGroup());
            info.setCategory(cat);
            info.setMuscles(List.of());
            info.setMusclesSecondary(List.of());
            info.setEquipments(List.of());
            exercises.put(entry.getExercise(), info);
        }
    }

    @Override
    public WgerExerciseInfo getExerciseDetails(String name) {
        return exercises.get(name);
    }

    @Override
    public String getProviderName() {
        return "Local JSON (" + exercises.size() + " exercises)";
    }
}
