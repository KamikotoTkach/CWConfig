package ru.cwcode.tkach.config.base;

import java.nio.file.Path;
import java.time.Duration;

public abstract class Platform {
  public abstract void info(String message);
  
  public abstract void warning(String message);
  
  public abstract void runAsync(Runnable runnable);
  
  public abstract void schedule(Runnable runnable, Duration frequency, boolean async);
  
  public abstract Path dataFolder();
  
  public abstract void disable();
}
