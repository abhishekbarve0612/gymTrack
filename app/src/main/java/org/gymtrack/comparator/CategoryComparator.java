package org.gymtrack.comparator;

import org.gymtrack.model.WgerExerciseInfo;

import java.util.Comparator;

public class CategoryComparator implements Comparator<WgerExerciseInfo> {

    @Override
    public int compare(WgerExerciseInfo e1, WgerExerciseInfo e2) {
        String category1 = e1.getCategory() != null ? e1.getCategory().getName() : "";
        String category2 = e2.getCategory() != null ? e2.getCategory().getName() : "";

        return category1.compareTo(category2);
    }
}
