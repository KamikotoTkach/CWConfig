package ru.cwcode.tkach.config.data.range;

import ru.cwcode.cwutils.numbers.Rand;

import java.io.Serializable;

public record IntRange(int min, int max) implements Serializable {
  public int random() {
    if (min >= max) return max;

    return Rand.ofInt(min, max + 1);
  }
  
  public boolean contains(int value) {
    return value >= min && value <= max;
  }
}
