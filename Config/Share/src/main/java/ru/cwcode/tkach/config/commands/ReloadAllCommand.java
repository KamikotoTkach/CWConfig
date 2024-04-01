package ru.cwcode.tkach.config.commands;

import ru.cwcode.commands.executor.CommonExecutor;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.manager.ConfigManager;

import java.util.function.Consumer;

public class ReloadAllCommand<C extends Config<C>> extends CommonExecutor {
  ConfigManager<C> configManager;
  Consumer<C> onReload = c -> {};
  
  public ReloadAllCommand(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }
  
  public ReloadAllCommand(ConfigManager<C> configManager, Consumer<C> onReload) {
    this(configManager);
    this.onReload = onReload;
  }
  
  @Override
  public void executeForPlayer() {
    configManager.reloadAll(sender).forEach(onReload);
  }
}