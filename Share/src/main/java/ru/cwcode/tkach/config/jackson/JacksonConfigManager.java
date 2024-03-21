package ru.cwcode.tkach.config.jackson;

import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.Platform;
import ru.cwcode.tkach.config.base.manager.*;

public abstract class JacksonConfigManager<C extends Config<C>> extends ConfigManager<C> {
  
  public JacksonConfigManager(Platform platform, ConfigPersister<C> persister, ConfigMapper<C> mapper) {
    super(platform, new ConfigLoader<>(), persister, mapper, new ConfigCreator<>());
  }
}
