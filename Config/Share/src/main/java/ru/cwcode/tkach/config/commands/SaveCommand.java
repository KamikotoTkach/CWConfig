package ru.cwcode.tkach.config.commands;

import ru.cwcode.commands.executor.CommonExecutor;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.manager.ConfigManager;

import java.util.function.Consumer;

import static ru.cwcode.tkach.config.server.ServerPlatform.l10n;

public class SaveCommand<C extends Config<C>> extends CommonExecutor {
  ConfigManager<C> configManager;
  
  public SaveCommand(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }
  
  @Override
  public void executeForPlayer() {
    configManager.findConfig(argS(0)).ifPresent(config -> {
      configManager.save(config);
      sender.sendMessage(l10n.get("config.command.executed"));
    });
  }
}
