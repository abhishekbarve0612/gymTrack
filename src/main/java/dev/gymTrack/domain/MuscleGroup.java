package dev.gymTrack.domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum MuscleGroup {
    CHEST("Chest", BodyRegion.UPPER),
    BACK("Back", BodyRegion.UPPER),
    SHOULDERS("Shoulders", BodyRegion.UPPER),
    BICEPS("Biceps", BodyRegion.UPPER),
    TRICEPS("Triceps", BodyRegion.UPPER),
    QUADS("Quads", BodyRegion.LOWER),
    HAMSTRINGS("Hamstrings", BodyRegion.LOWER),
    GLUTES("Glutes", BodyRegion.LOWER),
    CALVES("Calves", BodyRegion.LOWER),
    ABS("Abs", BodyRegion.CORE);

    private static  final Map<String, MuscleGroup> BY_LABEL = buildLookup();
    private final String label;
    private final BodyRegion region;


    MuscleGroup(String label, BodyRegion region) {
        this.label = label;
        this.region = region;
    }

    private static Map<String, MuscleGroup> buildLookup() {
        Map<String, MuscleGroup> map = new HashMap<>();
        for (MuscleGroup m : values()) {
            map.put(m.label.toLowerCase(Locale.ROOT), m);
        }
        return Collections.unmodifiableMap(map);
    }

    public static MuscleGroup fromLabel(String text) {
        MuscleGroup match = BY_LABEL.get(text.strip().toLowerCase(Locale.ROOT));
        if (match == null) {
            throw new IllegalArgumentException("Unknown muscle group: '" + text + "'");
        }
        return match;
    }
}
