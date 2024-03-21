package ru.cwcode.tkach.config.base.manager;

import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;

import java.util.Optional;

public abstract class ConfigMapper<C extends Config<C>> {
  ConfigManager<C> configManager;
  
  public void setConfigManager(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }
  
  public abstract <V extends C> Optional<V> map(String string, Class<V> configClass, ConfigPersistOptions persistOptions);
  
  public abstract Optional<String> map(C config, ConfigPersistOptions persistOptions);
}
