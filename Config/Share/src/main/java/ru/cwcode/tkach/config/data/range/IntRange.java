package ru.cwcode.tkach.config.data.range;

import ru.cwcode.cwutils.numbers.Rand;

public record IntRange(int min, int max) {
  public int random() {
    return Rand.ofInt(min, max);
  }
}
