package ru.cwcode.tkach.config.data.range;

import ru.cwcode.cwutils.numbers.Rand;

import java.io.Serializable;

public record DoubleRange(double min, double max) implements Serializable {
  public double random() {
    return Rand.ofDouble(min, max);
  }
  public boolean contains(double value) {
    return value >= min && value <= max;
  }
}
