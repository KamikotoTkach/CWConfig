package ru.cwcode.tkach.config.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import ru.cwcode.commands.executor.CommonExecutor;
import ru.cwcode.cwutils.files.PastesDevClient;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;
import ru.cwcode.tkach.config.base.manager.ConfigManager;
import ru.cwcode.tkach.config.base.manager.ConfigMapper;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static ru.cwcode.tkach.config.server.ServerPlatform.l10n;

public class FetchCommand<C extends Config<C>> extends CommonExecutor {
  ConfigManager<C> configManager;
  
  public FetchCommand(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }
  
  @Override
  public void executeForPlayer() {
    configManager.findConfig(argS(0)).ifPresent(config -> {
      String url = argS(1);
      
      sender.sendMessage(l10n.get("config.command.fetch.fetching", config.name()));
      
      CompletableFuture.supplyAsync(() -> {
        try {
          return PastesDevClient.getPaste(url);
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
          return null;
        }
      }).thenAccept(content -> {
        if (content == null) {
          sender.sendMessage(l10n.get("config.command.fetch.failed", config.name()));
        } else {
          Class<C> configType = (Class<C>) config.getClass();
          var mappedConfig = configManager.mapper().map(content, configType, ConfigPersistOptions.DEFAULT);
          mappedConfig.getConfig().ifPresentOrElse(c -> {
            configManager.updateConfig(config.name(), c);
            sender.sendMessage(Component.text(l10n.get("config.command.fetch.success", config.name()), this.command.getColorScheme().main()));
          }, () -> {
            sender.sendMessage(l10n.get("config.command.fetch.mapping.failed", config.name()));
          });
        }
      }).exceptionally(throwable -> {
        sender.sendMessage(l10n.get("config.command.fetch.failed", config.name()));
        return null;
      });
    });
  }
}
