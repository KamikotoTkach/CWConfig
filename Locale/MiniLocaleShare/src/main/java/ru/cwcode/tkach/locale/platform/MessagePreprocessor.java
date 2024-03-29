package ru.cwcode.tkach.locale.platform;

import net.kyori.adventure.audience.Audience;

public interface MessagePreprocessor {
  String preprocess(String message, Audience receiver);
}
