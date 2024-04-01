package ru.cwcode.tkach.config.base.manager;

import ru.cwcode.tkach.config.Utils;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;

import java.util.Optional;

public class ConfigCreator<C extends Config<C>> {
  ConfigManager<C> configManager;

  public void setConfigManager(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }

  public <V extends C> Optional<V> create(String name, Class<V> configClass, ConfigPersistOptions persistOptions) {

    if (!persistOptions.isSilent()) {
      configManager.platform.info("Создание дефолтного конфига %s".formatted(name));
    }

    Optional<V> instance = Utils.getNewInstance(configClass);

    if (!persistOptions.isSilent()) {
      instance.ifPresentOrElse(__ -> configManager.platform.info("Конфиг %s создан".formatted(name)),
                               () -> configManager.platform.info("Не удалось создать конфиг %s".formatted(name)));
    }

    return instance;
  }
}
