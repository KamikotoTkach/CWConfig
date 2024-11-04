package ru.cwcode.tkach.config.jackson;

import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPlatform;
import ru.cwcode.tkach.config.base.manager.*;

public abstract class JacksonConfigManager<C extends Config<C>> extends ConfigManager<C> {
  public JacksonConfigManager(ConfigPlatform configPlatform, ConfigLoader<C> loader, ConfigPersister<C> persister, ConfigMapper<C> mapper, ConfigCreator<C> creator) {
    super(configPlatform, loader, persister, mapper, creator);
  }
  
  public JacksonConfigManager(ConfigPlatform configPlatform, ConfigPersister<C> persister, ConfigMapper<C> mapper) {
    super(configPlatform, new ConfigLoader<>(), persister, mapper, new ConfigCreator<>());
  }
}
