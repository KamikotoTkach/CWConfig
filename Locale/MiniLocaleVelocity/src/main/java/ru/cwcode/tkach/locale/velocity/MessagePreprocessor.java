package ru.cwcode.tkach.locale.velocity;

import net.kyori.adventure.audience.Audience;
import ru.cwcode.tkach.locale.LegacyToMiniMessageReplacer;

public class MessagePreprocessor implements ru.cwcode.tkach.locale.platform.MessagePreprocessor {
  @Override
  public String preprocess(String message, Audience receiver) {
    return LegacyToMiniMessageReplacer.replace(message);
  }
}
