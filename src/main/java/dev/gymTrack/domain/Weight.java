package dev.gymTrack.domain;

import java.math.BigDecimal;

public record Weight(long grams) implements Comparable<Weight> {

    private static final double GRAMS_PER_KG = 1_000.0;
    private static final double GRAMS_PER_LB = 453.59237;

    public static final Weight ZERO = new Weight(0);

    public Weight {
        if (grams < 0) {
            throw new IllegalArgumentException("Weight cannot be negative!");
        }
    }

    public  static Weight kg(double kilograms) {
        return of(kilograms, GRAMS_PER_KG);
    }

    public static Weight lb(double pounds) {
        return of(pounds, GRAMS_PER_LB);
    }

    public static Weight of(double amount, double gramsPerUnit) {
        if (!Double.isFinite(amount)) {
            throw new IllegalArgumentException("Weight must be a finite number, got " + amount);
        }

        return new Weight(Math.round(amount * gramsPerUnit));
    }

    public double toKg() {
        return grams / GRAMS_PER_KG;
    }

    public double toLb() {
        return grams / GRAMS_PER_LB;
    }

    public Weight plus(Weight other) {
        return new Weight(Math.addExact(grams, other.grams));
    }

    public Weight times(int factor) {
        return new Weight(Math.multiplyExact(grams, factor));
    }

    @Override
    public int compareTo(Weight other) {
        return Long.compare(grams, other.grams);
    }

    @Override
    public String toString() {
        return BigDecimal.valueOf(grams, 3).stripTrailingZeros().toPlainString() + " kg";
    }
}
