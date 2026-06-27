package org.gymtrack.service;

import org.gymtrack.model.OverloadResult;
import org.gymtrack.model.WorkoutEntry;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class OverloadCalculator {

    public static List<OverloadResult> calculate(List<WorkoutEntry> log) {
        Map<String, List<WorkoutEntry>> byExercise = new LinkedHashMap<>();
        for (WorkoutEntry entry: log) {
            byExercise.computeIfAbsent(
                    entry.getExercise(),
                    k -> new ArrayList<>()
            ).add(entry);
        }

        List<OverloadResult> results = new ArrayList<>();

        for (Map.Entry<String, List<WorkoutEntry>> entry: byExercise.entrySet()) {
            List<WorkoutEntry> es = entry.getValue();
            if (es.size() < 2) continue;

            es.sort(Comparator.comparing(WorkoutEntry::getDate));
            WorkoutEntry first = es.getFirst();
            WorkoutEntry last = es.getLast();

            double startVol = first.getVolume(), endVol = last.getVolume();

            long days = ChronoUnit.DAYS.between(
                    LocalDate.parse(first.getDate()),
                    LocalDate.parse(last.getDate())
            );
            if (days <= 0 || startVol <= 0) continue;

            double absoluteGain = (endVol / startVol) - 1;
            double overloadRate = Math.pow(endVol / startVol, 365.0 / days) - 1;

            results.add(
                    new OverloadResult(
                            entry.getKey(),
                            absoluteGain,
                            overloadRate,
                            startVol,
                            endVol,
                            (int) days
                    )
            );
        }
        Collections.sort(results);
        return results;
    }
}
