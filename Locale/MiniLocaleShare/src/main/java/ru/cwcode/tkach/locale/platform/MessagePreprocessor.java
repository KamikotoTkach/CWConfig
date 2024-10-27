package ru.cwcode.tkach.locale.platform;

import net.kyori.adventure.audience.Audience;

import java.util.Arrays;
import java.util.List;

public interface MessagePreprocessor {
  String preprocess(String message, Audience receiver);
  
  default String[] preprocess(String[] message, Audience receiver) {
    String[] preprocessed = new String[message.length];
    
    for (int i = 0; i < message.length; i++) {
      preprocessed[i] = preprocess(message[i], receiver);
    }
    
    return preprocessed;
  }
  
  default List<String> preprocess(List<String> message, Audience receiver) {
    return Arrays.asList(preprocess(message.toArray(new String[0]), receiver));
  }
}
