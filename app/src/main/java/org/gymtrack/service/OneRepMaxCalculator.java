package org.gymtrack.service;

import org.gymtrack.model.OneRepMaxResult;
import org.gymtrack.model.WorkoutEntry;

import java.util.*;

public class OneRepMaxCalculator {

    public static List<OneRepMaxResult> calculate(List<WorkoutEntry> log) {
        Map<String, List<WorkoutEntry>> byExercise = new LinkedHashMap<>();
        for (WorkoutEntry entry : log) {
            byExercise.computeIfAbsent(
                    entry.getExercise(),
                    k -> new ArrayList<>()).add(entry);
        }

        List<OneRepMaxResult> results = new ArrayList<>();

        for (Map.Entry<String, List<WorkoutEntry>> entry : byExercise.entrySet()) {
            String exerciseName = entry.getKey();
            List<WorkoutEntry> sets = entry.getValue();

            WorkoutEntry bestEntry = null;
            double bestEstimate = -1.0;

            for (WorkoutEntry set : sets) {
                double weight = set.getWeightKg();
                int reps = set.getReps();

                if (weight <= 0 || reps <= 0)
                    continue;

                double estimate = epley(weight, reps);

                if (estimate > bestEstimate) {
                    bestEstimate = estimate;
                    bestEntry = set;
                }
            }

            if (bestEntry != null) {
                results.add(new OneRepMaxResult(
                        exerciseName,
                        bestEstimate,
                        bestEntry.getWeightKg(),
                        bestEntry.getReps(),
                        bestEntry.getDate()));
            }
        }

        Collections.sort(results);
        return results;
    }

    private static double epley(double weight, int reps) {
        if (reps == 1)
            return weight;
        return weight * (1.0 + reps / 30.0);
    }
}
