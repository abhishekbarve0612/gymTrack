package org.gymtrack;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.gymtrack.comparator.CategoryComparator;
import org.gymtrack.comparator.MuscleCountComparator;
import org.gymtrack.comparator.NameComparator;
import org.gymtrack.model.OneRepMaxResult;
import org.gymtrack.model.OverloadResult;
import org.gymtrack.model.WgerExerciseInfo;
import org.gymtrack.model.WorkoutEntry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gymtrack.provider.LocalExerciseProvider;
import org.gymtrack.provider.WgerApiProvider;
import org.gymtrack.provider.WorkoutDataProvider;
import org.gymtrack.service.ExerciseApiClient;
import org.gymtrack.service.OneRepMaxCalculator;
import org.gymtrack.service.OverloadCalculator;

public class App {

    public static List<WorkoutEntry> readLog(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = App.class.getClassLoader().getResourceAsStream((filename));

        if (is == null) {
            throw new IOException(("File not found: " + filename));
        }
        return mapper.readValue(is, new TypeReference<>() {
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
        for (WorkoutEntry e : log)
            uniqueNames.add(e.getExercise());

        List<WgerExerciseInfo> exercises = new ArrayList<>();
        for (String name : uniqueNames) {
            System.out.println("Fetching exercise: " + name);
            WgerExerciseInfo info = client.fetchExerciseInfo(name);
            if (info != null)
                exercises.add(info);
        }

        System.out.println("\n\n" + exercises.size() + " exercises found\n\n");

        exercises.sort(new MuscleCountComparator());
        System.out.println(" --- By muscle count (compound first) --- ");
        for (WgerExerciseInfo ex : exercises) {
            System.out.printf(
                    "   %d muscles  %s [%s]%n",
                    ex.getTotalMuscleCount(),
                    ex.getName(),
                    ex.getCategory() != null ? ex.getCategory().getName() : "?");
        }

        exercises.sort(new CategoryComparator());
        System.out.println(" --- By category --- ");
        for (WgerExerciseInfo ex : exercises) {
            System.out.printf(
                    "  %-12s  %s%n",
                    ex.getCategory() != null ? ex.getCategory().getName() : "?",
                    ex.getName());
        }

        exercises.sort(new NameComparator());
        System.out.println(" --- By name --- ");
        for (WgerExerciseInfo ex : exercises) {
            System.out.println(" " + ex.getName());
        }
    }

    public static void calculateOverloadRates(
            WorkoutDataProvider provider,
            List<WorkoutEntry> log) {
        System.out.println("Provider: " + provider.getProviderName());
        Set<String> seen = new HashSet<>();

        for (WorkoutEntry entry : log) {
            if (seen.add(entry.getExercise())) {
                WgerExerciseInfo info = provider.getExerciseDetails(entry.getExercise());
                if (info != null) {
                    System.out.println("     -> " + info);
                }
            }
        }

        List<OverloadResult> results = OverloadCalculator.calculate(log);
        System.out.println("\n Ranked by Overload Rates:");
        int rank = 1;
        for (OverloadResult result : results) {
            System.out.printf("  %d. %s%n"
                    + "     Volume: %.0f → %.0f kg (%+.0f%%)%n"
                    + "     Overload Rate: %+.1f%% annualized  (%d days)%n%n",
                    rank++,
                    result.getExercise(),
                    result.getStartVolume(),
                    result.getEndVolume(),
                    result.getAbsoluteGain() * 100,
                    result.getOverloadRate() * 100,
                    result.getDaysBetween());
        }
    }

    public static void calculateOneRepMax(List<WorkoutEntry> log) {
        List<OneRepMaxResult> results = OneRepMaxCalculator.calculate(log);

        System.out.println("\n===== ESTIMATED 1-REP MAXES (Epley Formula) =====");
        int rank = 1;
        for (OneRepMaxResult result : results) {
            System.out.printf("  %d. %s%n"
                    + "     Best Set : %.1f kg × %d reps  (%s)%n"
                    + "     Est. 1RM  : %.1f kg%n%n",
                    rank++,
                    result.getExercise(),
                    result.getBestWeight(),
                    result.getBestReps(),
                    result.getDate(),
                    result.getEstimatedOneRepMax());
        }
    }

    public static void main(String[] args) {
        try {
            List<WorkoutEntry> log = readLog("workout_log.json");

            // printSummary(log);

            System.out.println("============ All Entries ============");

            // for (WorkoutEntry entry : log) {
            // System.out.println(entry.toString());
            //
            // }

            fetchAndSortExercises(log);

            System.out.println("==================================");

            WorkoutDataProvider wger = new WgerApiProvider();
            WorkoutDataProvider local = new LocalExerciseProvider(log);

            calculateOverloadRates(wger, log);
            calculateOverloadRates(local, log);

            calculateOneRepMax(log);

        } catch (IOException e) {
            System.err.println("Error reading workout log: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
