package ru.cwcode.tkach.locale.paper.old;

import net.kyori.adventure.audience.Audience;
import ru.cwcode.tkach.locale.LegacyToMiniMessageReplacer;
import ru.cwcode.tkach.locale.paper.papi.PapiProcessor;
import ru.cwcode.tkach.locale.paper.papi.PapiWrapper;

public class MessagePreprocessor implements ru.cwcode.tkach.locale.platform.MessagePreprocessor {
  
  @Override
  public String preprocess(String message, Audience receiver) {
    String papiProcessed = PapiWrapper.isPapiLoaded() ? PapiProcessor.process(message, receiver) : message;
    
    return LegacyToMiniMessageReplacer.replace(papiProcessed);
  }
}
