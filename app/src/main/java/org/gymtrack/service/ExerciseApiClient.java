package org.gymtrack.service;

import java.util.HashMap;
import java.util.Map;

import org.gymtrack.model.WgerExerciseInfo;
import org.springframework.web.client.RestTemplate;

public class ExerciseApiClient {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://wger.de/api/v2";

    private static final Map<String, Integer> EXERCISE_IDS = new HashMap<>();

    static {
        EXERCISE_IDS.put("Bench Press", 192);
        EXERCISE_IDS.put("Squat", 365);
        EXERCISE_IDS.put("Deadlift", 105);
        EXERCISE_IDS.put("Overhead Press", 119);
        EXERCISE_IDS.put("Barbell Row", 106);
    }

    public ExerciseApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public WgerExerciseInfo fetchExerciseInfo(String exerciseName) {
        Integer id = EXERCISE_IDS.get(exerciseName);

        if (id == null) {
            System.out.println("Exercise not found: " + exerciseName);
            return null;
        }

        String url = BASE_URL + "/exerciseinfo/" + id + "/?format=json";
        System.out.println("Fetching: " + url);

        return restTemplate.getForObject(url, WgerExerciseInfo.class);
    }

    public WgerExerciseInfo fetchById(int id) {
        String url = BASE_URL + "/exerciseinfo/" + id + "/?format=json";
        System.out.println("Fetching: " + url);

        return restTemplate.getForObject(url, WgerExerciseInfo.class);
    }
}
