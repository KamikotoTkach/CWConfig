package ru.cwcode.tkach.locale.minilocalenew;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.cwcode.tkach.locale.Placeholders;
import ru.cwcode.tkach.locale.platform.MiniLocale;
import ru.cwcode.tkach.locale.wrapper.adventure.MiniMessageWrapper;

import java.util.UUID;

public class MiniLocaleNew extends MiniLocale {
  MiniMessageWrapperNew miniMessageWrapper = new MiniMessageWrapperNew();
  MessagePreprocessor messagePreprocessor = new MessagePreprocessor();
  
  @Override
  public Component legacySection(String message) {
    return LegacyComponentSerializer.legacySection().deserialize(message);
  }
  
  @Override
  public String getLanguage(Audience receiver) {
    return receiver instanceof Player player ? player.locale().getLanguage() : null;
  }
  
  @Override
  public boolean isPlayer(Audience audience) {
    return audience instanceof Player;
  }
  
  @Override
  public Audience getOnlinePlayer(UUID uuid) {
    return Bukkit.getPlayer(uuid);
  }
  
  @Override
  public Audience getPlayer(String name) {
    return Bukkit.getPlayer(name);
  }
  
  @Override
  public String plain(Component component) {
    return PlainTextComponentSerializer.plainText().serialize(component);
  }
  
  @Override
  public Audience getOnlinePlayers() {
    return Audience.audience(Bukkit.getOnlinePlayers());
  }
  
  @Override
  public MiniMessageWrapper miniMessageWrapper() {
    return miniMessageWrapper;
  }
  
  @Override
  public ru.cwcode.tkach.locale.platform.MessagePreprocessor messagePreprocessor() {
    return messagePreprocessor;
  }
  
  @Override
  public Audience console() {
    return Bukkit.getConsoleSender();
  }
  
  @Override
  public String legacyAmpersand(Component component) {
    return LegacyComponentSerializer.legacyAmpersand().serialize(component);
  }
  
  @Override
  public Component legacyAmpersand(String message) {
    return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
  }
  
  @Override
  public Placeholders emptyPlaceholders() {
    return new PlaceholdersNew();
  }
}
