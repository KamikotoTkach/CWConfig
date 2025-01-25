package ru.cwcode.tkach.config.data.range;

import ru.cwcode.cwutils.numbers.Rand;

public record DoubleRange(double min, double max) {
  public double random() {
    return Rand.ofDouble(min, max);
  }
}
