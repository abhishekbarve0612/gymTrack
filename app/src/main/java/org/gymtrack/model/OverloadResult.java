package org.gymtrack.model;

public class OverloadResult implements  Comparable<OverloadResult> {

    private final String exercise;
    private final double absoluteGain, overloadRate, startVolume, endVolume;
    private final int daysBetween;

    public OverloadResult(
            String exercise, double absoluteGain, double overloadRate,
            double startVolume, double endVolume, int daysBetween
    ) {
        this.exercise = exercise;
        this.absoluteGain = absoluteGain;
        this.overloadRate = overloadRate;
        this.startVolume = startVolume;
        this.endVolume = endVolume;
        this.daysBetween = daysBetween;
    }

    public String getExercise() {
        return exercise;
    }

    public double getAbsoluteGain() {
        return absoluteGain;
    }

    public double getOverloadRate() {
        return overloadRate;
    }

    public double getStartVolume() {
        return startVolume;
    }

    public double getEndVolume() {
        return endVolume;
    }

    public int getDaysBetween() {
        return daysBetween;
    }

    @Override
    public int compareTo(OverloadResult or) {
        return Double.compare(or.overloadRate, this.overloadRate);
    }
}
