package ru.cwcode.tkach.config.base.manager;

import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;

import java.util.Optional;

public abstract class ConfigMapper<C extends Config<C>> {
  protected ConfigManager<C> configManager;
  
  public void setConfigManager(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }
  
  public abstract <V extends C> MappingResult<V> map(String string, Class<V> configClass, ConfigPersistOptions persistOptions);
  
  public abstract Optional<String> map(C config, ConfigPersistOptions persistOptions);
  
  public record MappingException(int line, int column, String message) {}
  
  public class MappingResult<V extends C> {
    final V config;
    final MappingException exception;
    
    public MappingResult(V config, MappingException exception) {
      this.config = config;
      this.exception = exception;
    }
    
    public Optional<V> getConfig() {
      return Optional.ofNullable(config);
    }
    
    public Optional<MappingException> getException() {
      return Optional.ofNullable(exception);
    }
  }
}
