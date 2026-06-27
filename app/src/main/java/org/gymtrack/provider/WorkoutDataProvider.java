package org.gymtrack.provider;

import org.gymtrack.model.WgerExerciseInfo;

public interface WorkoutDataProvider {

    WgerExerciseInfo getExerciseDetails(String exerciseName);

    String getProviderName();
}
