package ru.cwcode.tkach.config.base;

public class ConfigPersistOptions {
  public static final ConfigPersistOptions DEFAULT = new ConfigPersistOptions();
  boolean async = false;
  boolean silent = false;
  boolean logProgress = true;
  
  public ConfigPersistOptions setAsync() {
    this.async = true;
    return this;
  }
  
  public ConfigPersistOptions setSilent() {
    this.silent = true;
    return this;
  }
  
  public ConfigPersistOptions async(boolean isAsync) {
    this.async = isAsync;
    return this;
  }
  
  public ConfigPersistOptions silent(boolean isSilent) {
    this.silent = isSilent;
    return this;
  }

  public ConfigPersistOptions logProgress(boolean logProgress) {
    this.logProgress = logProgress;
    return this;
  }
  
  public boolean isAsync() {
    return async;
  }
  
  public boolean isSilent() {
    return silent;
  }

  public boolean isLogProgress() {
    return logProgress;
  }
}
