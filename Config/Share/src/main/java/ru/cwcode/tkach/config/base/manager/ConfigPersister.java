package ru.cwcode.tkach.config.base.manager;

import ru.cwcode.tkach.config.Utils;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;

import java.nio.file.Path;

import static ru.cwcode.tkach.config.server.ServerPlatform.l10n;

public class ConfigPersister<C extends Config<C>> {
  ConfigManager<C> configManager;
  Preprocessor<C> preprocessor = new Preprocessor<>();
  
  public void setConfigManager(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }
  
  public void persist(C config, String data, Path path, ConfigPersistOptions options) {
    if (!options.isSilent()) {
      configManager.platform.info(l10n.get("config.persister.persisting", config.name()));
    }
    
    data = preprocessor.preprocess(config, data);
    
    if (Utils.writeString(path, data)) {
      if (!options.isSilent()) {
        configManager.platform.info(l10n.get("config.persister.persisted", config.name()));
      }
    } else {
      if (!options.isSilent()) {
        configManager.platform.warning(l10n.get("config.persister.cantPersist", config.name()));
      }
    }
  }
  
  public void setPreprocessor(Preprocessor<C> preprocessor) {
    this.preprocessor = preprocessor;
  }
  
  public static class Preprocessor<C extends Config<C>> {
    public String preprocess(C config, String data) {
      return data;
    }
  }
}
