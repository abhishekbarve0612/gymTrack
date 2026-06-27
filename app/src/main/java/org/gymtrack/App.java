package org.gymtrack;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.gymtrack.comparator.CategoryComparator;
import org.gymtrack.comparator.MuscleCountComparator;
import org.gymtrack.comparator.NameComparator;
import org.gymtrack.model.WgerExerciseInfo;
import org.gymtrack.model.WorkoutEntry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gymtrack.service.ExerciseApiClient;

public class App {

    public static List<WorkoutEntry> readLog(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = App.class.getClassLoader().getResourceAsStream((filename));

        if (is == null) {
            throw new IOException(("File not fofund: " + filename));
        }
        return mapper.readValue(is, new TypeReference<List<WorkoutEntry>>() {
        });
    }

    public static void printSummary(List<WorkoutEntry> log) {

        Set<String> exercises = new LinkedHashSet<>();
        Map<String, Integer> byMuscle = new TreeMap<>();
        double totalVolume = 0;
        int completed = 0;

        for (WorkoutEntry e : log) {
            exercises.add(e.getExercise());
            byMuscle.merge(e.getMuscleGroup(), 1, Integer::sum);
            totalVolume += e.getVolume();

            if (e.isCompleted())
                completed++;
        }

        System.out.println("===== WORKOUT SUMMARY =====");
        System.out.println("Total exercises: " + exercises.size());
        System.out.println("By muscle group: " + byMuscle);
        System.out.println("Total volume: " + totalVolume);
        System.out.println("Completed: " + completed);
        System.out.println("============================");
    }

    public static void fetchAndSortExercises(List<WorkoutEntry> log) {
        ExerciseApiClient client = new ExerciseApiClient();

        Set<String> uniqueNames = new LinkedHashSet<>();
        for (WorkoutEntry e: log) uniqueNames.add(e.getExercise());

        List<WgerExerciseInfo> exercises = new ArrayList<>();
        for (String name: uniqueNames) {
            System.out.println("Fetching exercise: " + name);
            WgerExerciseInfo info = client.fetchExerciseInfo(name);
            if (info != null) exercises.add(info);
        }

        System.out.println("\n\n" + exercises.size() + " exercises found\n\n");

        exercises.sort(new MuscleCountComparator());
        System.out.println(" --- By muscle count (compound first) --- ");
        for (WgerExerciseInfo ex: exercises) {
            System.out.printf(
                    "   %d muscles  %s [%s]%n",
                    ex.getTotalMuscleCount(),
                    ex.getName(),
                    ex.getCategory() != null ? ex.getCategory().getName() : "?"
            );
        }

        exercises.sort(new CategoryComparator());
        System.out.println(" --- By category --- ");
        for (WgerExerciseInfo ex: exercises) {
            System.out.printf(
                    "  %-12s  %s%n",
                    ex.getCategory() != null ? ex.getCategory().getName() : "?",
                    ex.getName());
        }

        exercises.sort(new NameComparator());
        System.out.println(" --- By name --- ");
        for (WgerExerciseInfo ex: exercises) {
            System.out.println(" " + ex.getName());
        }
    }

    public static void main(String[] args) {
        try {
            List<WorkoutEntry> log = readLog("workout_log.json");

//            printSummary(log);

            System.out.println("============ All Entries ============");

//            for (WorkoutEntry entry : log) {
//                System.out.println(entry.toString());
//
//            }

            fetchAndSortExercises(log);

            System.out.println("==================================");

        } catch (IOException e) {
            System.err.println("Error reading workout log: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
