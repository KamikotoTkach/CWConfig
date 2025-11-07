package ru.cwcode.tkach.config.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import ru.cwcode.commands.executor.CommonExecutor;
import ru.cwcode.cwutils.files.PastesDevClient;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.manager.ConfigManager;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static ru.cwcode.tkach.config.server.ServerPlatform.l10n;

public class ShareCommand<C extends Config<C>> extends CommonExecutor {
  ConfigManager<C> configManager;
  
  public ShareCommand(ConfigManager<C> configManager) {
    this.configManager = configManager;
  }
  
  @Override
  public void executeForPlayer() {
    configManager.findConfig(argS(0)).ifPresent(config -> {
      sender.sendMessage(l10n.get("config.command.share.uploading", config.name()));
      
      CompletableFuture.supplyAsync(() -> {
        try {
          return PastesDevClient.createPaste(config.toString(), PastesDevClient.PasteLanguage.yaml);
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
          return null;
        }
      }).thenAccept(url -> {
        if (url == null) {
          sender.sendMessage(l10n.get("config.command.share.failed", config.name()));
        } else {
          sender.sendMessage(Component.text(l10n.get("config.command.share.success", config.name(), url), this.command.getColorScheme().main())
                                      .clickEvent(ClickEvent.openUrl(url)));
        }
      }).exceptionally(throwable -> {
        sender.sendMessage(l10n.get("config.command.share.failed", config.name()));
        return null;
      });
    });
  }
}
