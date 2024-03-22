package ru.cwcode.tkach.locale.minilocaleold;

import net.kyori.adventure.audience.Audience;
import ru.cwcode.tkach.locale.minilocaleold.papi.PapiProcessor;
import ru.cwcode.tkach.locale.minilocaleold.papi.PapiWrapper;

public class MessagePreprocessor implements ru.cwcode.tkach.locale.platform.MessagePreprocessor {
  @Override
  public String preprocess(String message, Audience receiver) {
    return PapiWrapper.isPapiLoaded() ? PapiProcessor.process(message, receiver) : message;
  }
}
