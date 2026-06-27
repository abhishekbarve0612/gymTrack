package org.gymtrack.model;

public class OneRepMaxResult implements Comparable<OneRepMaxResult> {

    private final String exercise;
    private final double estimatedOneRepMax;
    private final double bestWeight;
    private final int bestReps;
    private final String date;

    public OneRepMaxResult(
            String exercise,
            double estimatedOneRepMax,
            double bestWeight,
            int bestReps,
            String date) {
        this.exercise = exercise;
        this.estimatedOneRepMax = estimatedOneRepMax;
        this.bestWeight = bestWeight;
        this.bestReps = bestReps;
        this.date = date;
    }

    public String getExercise() {
        return exercise;
    }

    public double getEstimatedOneRepMax() {
        return estimatedOneRepMax;
    }

    public double getBestWeight() {
        return bestWeight;
    }

    public int getBestReps() {
        return bestReps;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int compareTo(OneRepMaxResult other) {
        return Double.compare(other.estimatedOneRepMax, this.estimatedOneRepMax);
    }
}
