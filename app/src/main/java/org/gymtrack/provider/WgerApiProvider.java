package org.gymtrack.provider;

import org.gymtrack.model.WgerExerciseInfo;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class WgerApiProvider implements  WorkoutDataProvider {

    private final RestTemplate rest = new RestTemplate();
    private static final String BASE_URL = "https://wger.de/api/v2";

    private static final Map<String, Integer> EXERCISE_IDS = Map.of(
            "Bench Press", 73,
            "Squat", 1801,
            "Deadlift", 184,
            "Overhead Press", 687,
            "Barbell Row", 1698
    );

    @Override
    public WgerExerciseInfo getExerciseDetails(String name) {
        Integer id = EXERCISE_IDS.get(name);
        if (id == null) return null;

        String url = BASE_URL + "/exerciseinfo/" + id + "/?format=json";

        return rest.getForObject(url, WgerExerciseInfo.class);
    }

    @Override
    public  String getProviderName() {
        return "Wger API";
    }
}
