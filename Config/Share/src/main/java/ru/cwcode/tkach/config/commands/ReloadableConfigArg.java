package ru.cwcode.tkach.config.commands;

import ru.cwcode.commands.Argument;
import ru.cwcode.commands.api.Sender;
import ru.cwcode.tkach.config.annotation.Reloadable;
import ru.cwcode.tkach.config.base.manager.ConfigManager;

import java.util.Collection;
import java.util.Set;

public class ReloadableConfigArg extends Argument {
  ConfigManager<?> configManager;

  public ReloadableConfigArg(ConfigManager<?> configManager) {
    this.configManager = configManager;
  }

  @Override
  public boolean valid(String raw) {
    return getReloadableConfigNames().contains(raw);
  }

  @Override
  public Collection<String> completions(Sender sender) {
    return getReloadableConfigNames();
  }

  @Override
  public String argumentName() {
    return "config";
  }

  private Set<String> getReloadableConfigNames() {
    return configManager.getConfigNames(Reloadable.class::isInstance);
  }
}
