package org.gymtrack;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.gymtrack.model.WorkoutEntry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {

    public static List<WorkoutEntry> readLog(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = App.class.getClassLoader().getResourceAsStream(filename);

        if (is == null) {
            throw new IOException("File not found: " + filename);
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

    public static void main(String[] args) {
        try {
            List<WorkoutEntry> log = readLog("workout_log.json");

            printSummary(log);

            System.out.println("============ All Entries ============");

            for (WorkoutEntry entry : log) {
                System.out.println(entry.toString());

            }

            System.out.println("==================================");

        } catch (IOException e) {
            System.err.println("Error reading workout log: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
