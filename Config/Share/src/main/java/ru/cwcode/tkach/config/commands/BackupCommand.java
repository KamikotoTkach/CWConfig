package ru.cwcode.tkach.config.commands;

import ru.cwcode.commands.executor.CommonExecutor;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.manager.ConfigManager;

public class BackupCommand<C extends Config<C>> extends CommonExecutor {
  ConfigManager<C> configManager;
  
  public BackupCommand(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }
  
  @Override
  public void executeForPlayer() {
    configManager.findConfig(argS(0)).ifPresent(config -> {
      configManager.backupConfigFile(config.name(), (c) -> {});
      sender.sendMessage("Команда выполнена");
    });
  }
}
