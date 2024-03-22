package ru.cwcode.tkach.config.base;

import java.nio.file.Path;
import java.time.Duration;

public interface ConfigPlatform {
  void info(String message);
  
  void warning(String message);
  
  void runAsync(Runnable runnable);
  
  void schedule(Runnable runnable, Duration frequency, boolean async);
  
  Path dataFolder();
  
  void disable();
}
