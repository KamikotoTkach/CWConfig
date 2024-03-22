package ru.cwcode.tkach.locale.minilocalevelocity;

import net.kyori.adventure.audience.Audience;

public class MessagePreprocessor implements ru.cwcode.tkach.locale.platform.MessagePreprocessor {
  @Override
  public String preprocess(String message, Audience receiver) {
    return message;
  }
}
