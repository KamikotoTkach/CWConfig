package ru.cwcode.tkach.locale.paper.preprocessor;

import net.kyori.adventure.audience.Audience;
import ru.cwcode.tkach.locale.paper.papi.PapiProcessor;
import ru.cwcode.tkach.locale.paper.papi.PapiWrapper;
import ru.cwcode.tkach.locale.preprocessor.MessagePreprocessor;

public class PapiPreprocessor implements MessagePreprocessor {
  @Override
  public String preprocess(String message, Audience receiver) {
    return PapiWrapper.isPapiLoaded() ? PapiProcessor.process(message, receiver) : message;
  }
}
