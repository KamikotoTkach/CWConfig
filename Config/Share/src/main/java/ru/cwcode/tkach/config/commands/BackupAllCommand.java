package ru.cwcode.tkach.config.commands;

import ru.cwcode.commands.executor.CommonExecutor;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.manager.ConfigManager;

public class BackupAllCommand<C extends Config<C>> extends CommonExecutor {
  ConfigManager<C> configManager;
  
  public BackupAllCommand(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }
  
  @Override
  public void executeForPlayer() {
    configManager.getConfigNames(x -> true)
                 .forEach(x -> configManager.backupConfigFile(x, z -> {}));
    
    sender.sendMessage("Команда выполнена");
  }
}