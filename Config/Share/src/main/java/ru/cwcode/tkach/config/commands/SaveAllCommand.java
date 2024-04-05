package ru.cwcode.tkach.config.commands;

import ru.cwcode.commands.executor.CommonExecutor;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.manager.ConfigManager;

public class SaveAllCommand<C extends Config<C>> extends CommonExecutor {
  ConfigManager<C> configManager;
  
  public SaveAllCommand(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }
  
  @Override
  public void executeForPlayer() {
    configManager.saveAll();
    sender.sendMessage("Команда выполнена");
  }
}