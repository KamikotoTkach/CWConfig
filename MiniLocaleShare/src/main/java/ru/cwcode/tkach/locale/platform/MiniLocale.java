package ru.cwcode.tkach.locale.platform;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import ru.cwcode.tkach.locale.Placeholders;
import ru.cwcode.tkach.locale.wrapper.adventure.MiniMessageWrapper;

import java.util.UUID;

public abstract class MiniLocale {
  static MiniLocale instance;
  
  public abstract Component legacySection(String message);
  
  public abstract String getLanguage(Audience receiver);
  
  public static MiniLocale getInstance() {
    return instance;
  }
  
  public static void setInstance(MiniLocale instance) {
    MiniLocale.instance = instance;
  }
  
  public abstract boolean isPlayer(Audience audience);
  
  public abstract Audience getOnlinePlayer(UUID uuid);
  
  public abstract Audience getPlayer(String name);
  
  public abstract String plain(Component component);
  
  public abstract Audience getOnlinePlayers();
  
  public abstract MiniMessageWrapper miniMessageWrapper();
  
  public abstract MessagePreprocessor messagePreprocessor();
  
  public abstract Audience console();
  
  public abstract String legacyAmpersand(Component component);
  
  public abstract Component legacyAmpersand(String message);
  
  public abstract Placeholders emptyPlaceholders();
}
