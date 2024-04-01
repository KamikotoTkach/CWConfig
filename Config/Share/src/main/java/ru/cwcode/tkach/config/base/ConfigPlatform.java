package ru.cwcode.tkach.config.base;

import com.fasterxml.jackson.databind.Module;

import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

public interface ConfigPlatform {
  List<Module> additionalJacksonModules();
  
  void info(String message);
  
  void warning(String message);
  
  void runAsync(Runnable runnable);
  
  void schedule(Runnable runnable, Duration frequency, boolean async);
  
  Path dataFolder();
  
  void disable();
}
