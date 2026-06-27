package org.gymtrack.comparator;

import org.gymtrack.model.WgerExerciseInfo;

import java.util.Comparator;

public class MuscleCountComparator implements Comparator<WgerExerciseInfo> {

    @Override
    public int compare(WgerExerciseInfo e1, WgerExerciseInfo e2) {
        return Integer.compare(e1.getTotalMuscleCount(), e2.getTotalMuscleCount());
    }
}
