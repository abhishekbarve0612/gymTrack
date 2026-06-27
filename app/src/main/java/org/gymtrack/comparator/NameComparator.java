package org.gymtrack.comparator;

import org.gymtrack.model.WgerExerciseInfo;

import java.util.Comparator;

public class NameComparator implements Comparator<WgerExerciseInfo> {

    @Override
    public  int compare(WgerExerciseInfo e1, WgerExerciseInfo e2) {
        return e1.getName().compareTo(e2.getName());
    }
}
