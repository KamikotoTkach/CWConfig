package ru.cwcode.tkach.config.base.manager;

import ru.cwcode.tkach.config.Utils;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;

import java.nio.file.Path;
import java.util.Optional;

import static ru.cwcode.tkach.config.server.ServerPlatform.l10n;

public class ConfigLoader<C extends Config<C>> {
  ConfigManager<? extends C> configManager;
  Preprocessor preprocessor = Utils.getEnvironmentReplacerPreprocessor();
  
  public void setConfigManager(ConfigManager<? extends C> configManager) {
    this.configManager = configManager;
  }
  
  public Optional<String> load(String name, ConfigPersistOptions options) {
    if (!options.isSilent()) {
      configManager.platform.info(l10n.get("config.loader.loading", name));
    }
    
    Path path = configManager.getPath(name);
    
    String str = Utils.readString(path);
    
    if (str.isEmpty()) {
      if (!options.isSilent()) {
        configManager.platform.info(l10n.get("config.loader.cantLoad", name));
      }
      
      return Optional.empty();
    }
    
    if (!options.isSilent()) {
      configManager.platform.info(l10n.get("config.loader.loaded", name));
    }
    
    return Optional.of(preprocessor.preprocess(str));
  }
  
  public void addPreprocessor(ConfigLoader.Preprocessor preprocessor) {
    Preprocessor previous = this.preprocessor;
    
    this.preprocessor = (data) -> preprocessor.preprocess(previous.preprocess(data));
  }
  
  public interface Preprocessor {
    String preprocess(String data);
  }
}
