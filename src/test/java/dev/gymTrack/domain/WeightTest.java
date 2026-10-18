package dev.gymTrack.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class WeightTest {

    @Test
    void equalAmountsAreEqualWhateverTheUnit() {
        assertEquals(Weight.kg(0.4536), Weight.lb(1));
    }

    @Test
    void additionIsExact() {
        assertEquals(Weight.kg(0.3), Weight.kg(0.1).plus(Weight.kg(0.2)));
    }

    @Test
    void volumeMultipliesWithoutDrift() {
        assertEquals(Weight.kg(500), Weight.kg(62.5).times(8));
    }

    @Test
    void rejectsNegativeWeight() {
        assertThrows(IllegalArgumentException.class, () -> Weight.kg(-5));
    }

    @Test
    void rejectsNonNumbers() {
        assertThrows(IllegalArgumentException.class, () -> Weight.kg(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> Weight.lb(Double.POSITIVE_INFINITY));
    }

    @Test
    void ordersByMass() {
        assertTrue(Weight.kg(100).compareTo(Weight.lb(200)) > 0);
    }
}
