package org.gymtrack.service;

import java.util.HashMap;
import java.util.Map;

import org.gymtrack.model.WgerExerciseInfo;
import org.springframework.web.client.RestTemplate;

public class ExerciseApiClient {

    private final RestTemplate rest = new RestTemplate();
    private static final String BASE_URL = "https://wger.de/api/v2";

    private static final Map<String, Integer> EXERCISE_IDS = Map.of(
            "Bench Press", 73,
            "Squat", 1801,
            "Deadlift", 184,
            "Overhead Press", 687,
            "Barbell Row", 1698
    );

    public WgerExerciseInfo fetchExerciseInfo(String exerciseName) {
        Integer id = EXERCISE_IDS.get(exerciseName);
        if (id == null) {
            System.out.println("Unknown exercise: " + exerciseName);
            return null;
        }
        String url = BASE_URL + "/exerciseinfo/" + id + "/?format=json";
        return rest.getForObject(url, WgerExerciseInfo.class);
    }
}
