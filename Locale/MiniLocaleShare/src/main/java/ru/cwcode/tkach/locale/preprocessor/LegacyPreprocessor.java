package ru.cwcode.tkach.locale.preprocessor;

import net.kyori.adventure.audience.Audience;
import ru.cwcode.tkach.locale.LegacyToMiniMessageReplacer;

public class LegacyPreprocessor implements MessagePreprocessor {
  @Override
  public String preprocess(String message, Audience receiver) {
    return LegacyToMiniMessageReplacer.replace(message);
  }
}
