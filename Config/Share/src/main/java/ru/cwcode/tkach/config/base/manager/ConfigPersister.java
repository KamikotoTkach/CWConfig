package ru.cwcode.tkach.config.base.manager;

import ru.cwcode.tkach.config.Utils;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;

import java.nio.file.Path;

import static ru.cwcode.tkach.config.server.ServerPlatform.l10n;

public class ConfigPersister<C extends Config<C>> {
  ConfigManager<C> configManager;
  Preprocessor<C> preprocessor = (config, data) -> data;
  
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
  
  public void addPreprocessor(Preprocessor<C> preprocessor) {
    Preprocessor<C> previous = this.preprocessor;
    
    this.preprocessor = (config, data) -> preprocessor.preprocess(config, previous.preprocess(config, data));
  }
  
  public interface Preprocessor<C extends Config<C>> {
     String preprocess(C config, String data);
  }
}
