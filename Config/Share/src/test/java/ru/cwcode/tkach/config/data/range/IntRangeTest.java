package ru.cwcode.tkach.config.data.range;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntRangeTest {
    private static final int ROLLS = 10_000;

    @Test
    public void randomCoversBothBounds() {
        IntRange range = new IntRange(1, 3);
        Set<Integer> seen = new HashSet<>();

        for (int i = 0; i < ROLLS; i++) {
            int value = range.random();

            assertTrue("value " + value + " is out of range", range.contains(value));
            seen.add(value);
        }

        assertEquals(Set.of(1, 2, 3), seen);
    }

    @Test
    public void singleValueRangeIsConstant() {
        assertEquals(5, new IntRange(5, 5).random());
    }

    @Test
    public void invertedRangeFallsBackToMax() {
        assertEquals(3, new IntRange(5, 3).random());
    }
}
