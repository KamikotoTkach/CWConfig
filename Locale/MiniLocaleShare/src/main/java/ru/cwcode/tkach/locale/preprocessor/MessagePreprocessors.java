package ru.cwcode.tkach.locale.preprocessor;

import net.kyori.adventure.audience.Audience;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class MessagePreprocessors implements MessagePreprocessor {
  private volatile LinkedHashMap<String, MessagePreprocessor> preprocessors = new LinkedHashMap<>();
  
  @Override
  public String preprocess(String message, Audience receiver) {
    String current = message;
    
    for (Map.Entry<String, MessagePreprocessor> entry : preprocessors.entrySet()) {
      current = entry.getValue().preprocess(current, receiver);
    }
    
    return current;
  }
  
  public synchronized void addFirst(String name, MessagePreprocessor preprocessor) {
    checkDuplicate(name);
    LinkedHashMap<String, MessagePreprocessor> newMap = new LinkedHashMap<>();
    newMap.put(name, preprocessor);
    newMap.putAll(preprocessors);
    preprocessors = newMap;
  }
  
  public synchronized void addLast(String name, MessagePreprocessor preprocessor) {
    checkDuplicate(name);
    LinkedHashMap<String, MessagePreprocessor> newMap = new LinkedHashMap<>(preprocessors);
    newMap.put(name, preprocessor);
    preprocessors = newMap;
  }
  
  public synchronized void addBefore(String baseName, String name, MessagePreprocessor preprocessor) {
    checkDuplicate(name);
    checkExists(baseName);
    LinkedHashMap<String, MessagePreprocessor> newMap = new LinkedHashMap<>();
    
    for (Map.Entry<String, MessagePreprocessor> entry : preprocessors.entrySet()) {
      if (entry.getKey().equals(baseName)) {
        newMap.put(name, preprocessor);
      }
      newMap.put(entry.getKey(), entry.getValue());
    }
    
    preprocessors = newMap;
  }
  
  public synchronized void addAfter(String baseName, String name, MessagePreprocessor preprocessor) {
    checkDuplicate(name);
    checkExists(baseName);
    LinkedHashMap<String, MessagePreprocessor> newMap = new LinkedHashMap<>();
    
    for (Map.Entry<String, MessagePreprocessor> entry : preprocessors.entrySet()) {
      newMap.put(entry.getKey(), entry.getValue());
      if (entry.getKey().equals(baseName)) {
        newMap.put(name, preprocessor);
      }
    }
    
    preprocessors = newMap;
  }
  
  public synchronized void remove(String name) {
    checkExists(name);
    LinkedHashMap<String, MessagePreprocessor> newMap = new LinkedHashMap<>(preprocessors);
    newMap.remove(name);
    preprocessors = newMap;
  }
  
  private void checkDuplicate(String name) {
    if (preprocessors.containsKey(name)) {
      throw new IllegalArgumentException("Preprocessor with name '" + name + "' already exists");
    }
  }
  
  private void checkExists(String name) {
    if (!preprocessors.containsKey(name)) {
      throw new NoSuchElementException("Preprocessor with name '" + name + "' not found");
    }
  }
}

