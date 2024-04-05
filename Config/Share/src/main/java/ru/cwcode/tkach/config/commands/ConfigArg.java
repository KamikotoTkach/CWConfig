package ru.cwcode.tkach.config.commands;

import ru.cwcode.commands.Argument;
import ru.cwcode.commands.api.Sender;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.manager.ConfigManager;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

public class ConfigArg<C extends Config<C>> extends Argument {
  private final Predicate<C> predicate;
  ConfigManager<C> configManager;
  
  public ConfigArg(Predicate<C> predicate, ConfigManager<C> configManager) {
    this.predicate = predicate;
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
    return configManager.getConfigNames(predicate);
  }
}
