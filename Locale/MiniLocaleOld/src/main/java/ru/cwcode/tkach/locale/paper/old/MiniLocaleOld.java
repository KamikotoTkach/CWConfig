package ru.cwcode.tkach.locale.paper.old;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.Placeholders;
import ru.cwcode.tkach.locale.messageDirection.MessageDirection;
import ru.cwcode.tkach.locale.paper.old.placeholders.ComponentPlaceholderType;
import ru.cwcode.tkach.locale.paper.old.placeholders.StringPlaceholderType;
import ru.cwcode.tkach.locale.placeholders.PlaceholderTypesRegistry;
import ru.cwcode.tkach.locale.placeholders.PlaceholderTypesRegistryImpl;
import ru.cwcode.tkach.locale.platform.MiniLocale;
import ru.cwcode.tkach.locale.wrapper.adventure.MiniMessageWrapper;

import java.time.Duration;
import java.util.UUID;

public class MiniLocaleOld extends MiniLocale {
  MiniMessageWrapperOld miniMessageWrapper = new MiniMessageWrapperOld();
  MessagePreprocessor messagePreprocessor = new MessagePreprocessor();
  PlaceholderTypesRegistry placeholderTypesRegistry = new PlaceholderTypesRegistryImpl();
  
  {
    placeholderTypesRegistry.registerType(new StringPlaceholderType());
    placeholderTypesRegistry.registerType(new ComponentPlaceholderType());
  }
  
  @Override
  public Component legacySection(String message) {
    return LegacyComponentSerializer.legacySection().deserialize(message);
  }

  @Override
  public String getLanguage(Audience receiver) {
    return receiver instanceof Player player ? player.locale().getLanguage() : null;
  }

  @Override
  public void send(Message message, MessageDirection direction, Iterable<? extends Audience> audiences, Placeholders placeholders) {
    for (Audience audience : audiences) {
      if (audience instanceof ForwardingAudience forwardingAudience) {
        for (Audience a : forwardingAudience.audiences()) {
          direction.send(a, message.get(placeholders, a));
        }
      } else {
        direction.send(audience, message.get(placeholders, audience));
      }
    }
  }

  @Override
  public void showTitle(Audience audience, Component title, Component subtitle, int fadeIn, int stay, int fadeOut) {
    audience.showTitle(Title.title(title == null ? Component.empty() : title,
                                   subtitle == null ? Component.empty() : subtitle,
                                   Title.Times.of(Duration.ofMillis(fadeIn),
                                                  Duration.ofMillis(stay),
                                                  Duration.ofMillis(fadeOut))));
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
    return PlainComponentSerializer.plain().serialize(component);
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
  public MessagePreprocessor messagePreprocessor() {
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
    return new PlaceholdersOld();
  }
  
  @Override
  public PlaceholderTypesRegistry placeholderTypesRegistry() {
    return placeholderTypesRegistry;
  }
}
