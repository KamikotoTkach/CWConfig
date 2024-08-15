package ru.cwcode.tkach.config.base.manager;

import ru.cwcode.tkach.config.Utils;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;

import java.util.Optional;

import static ru.cwcode.tkach.config.server.ServerPlatform.l10n;

public class ConfigCreator<C extends Config<C>> {
  ConfigManager<C> configManager;
  
  public void setConfigManager(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }
  
  public <V extends C> Optional<V> create(String name, Class<V> configClass, ConfigPersistOptions persistOptions) {
    
    if (!persistOptions.isSilent()) {
      configManager.platform.info(l10n.get("config.creator.creating", name));
    }
    
    Optional<V> instance = Utils.getNewInstance(configClass);
    
    if (!persistOptions.isSilent()) {
      instance.ifPresentOrElse(__ -> configManager.platform.info(l10n.get("config.creator.created", name)),
                               () -> configManager.platform.info(l10n.get("config.creator.cantCreate", name)));
    }
    
    return instance;
  }
}
