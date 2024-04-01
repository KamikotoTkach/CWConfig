package ru.cwcode.tkach.config.base.manager;

import ru.cwcode.tkach.config.Utils;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;

import java.nio.file.Path;
import java.util.Optional;

public class ConfigLoader<C extends Config<C>> {
  ConfigManager<? extends C> configManager;

  public void setConfigManager(ConfigManager<? extends C> configManager) {
    this.configManager = configManager;
  }

  public Optional<String> load(String name, ConfigPersistOptions options) {
    if (!options.isSilent()) {
      configManager.platform.info("Загрузка файла конфига %s".formatted(name));
    }

    Path path = configManager.getPath(name);

    String str = Utils.readString(path);

    if (str.isEmpty()) {
      if (!options.isSilent()) {
        configManager.platform.info("Не удалось загрузить файл конфига %s".formatted(name));
      }

      return Optional.empty();
    }

    if (!options.isSilent()) {
      configManager.platform.info("Файл конфига %s успешно загружен".formatted(name));
    }

    return Optional.of(str);
  }
}
