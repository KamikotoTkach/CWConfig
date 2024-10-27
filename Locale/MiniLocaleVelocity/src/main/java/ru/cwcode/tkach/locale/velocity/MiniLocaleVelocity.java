package ru.cwcode.tkach.locale.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.Placeholders;
import ru.cwcode.tkach.locale.messageDirection.MessageDirection;
import ru.cwcode.tkach.locale.placeholders.PlaceholderTypesRegistry;
import ru.cwcode.tkach.locale.placeholders.PlaceholderTypesRegistryImpl;
import ru.cwcode.tkach.locale.platform.MiniLocale;
import ru.cwcode.tkach.locale.velocity.placeholders.ComponentLikePlaceholderType;
import ru.cwcode.tkach.locale.velocity.placeholders.StringPlaceholderType;
import ru.cwcode.tkach.locale.wrapper.adventure.MiniMessageWrapper;

import java.time.Duration;
import java.util.UUID;

public class MiniLocaleVelocity extends MiniLocale {
  MiniMessageWrapperVelocity miniMessageWrapper = new MiniMessageWrapperVelocity();
  MessagePreprocessor messagePreprocessor = new MessagePreprocessor();
  ProxyServer proxyServer;
  PlaceholderTypesRegistry placeholderTypesRegistry = new PlaceholderTypesRegistryImpl();
  
  {
    placeholderTypesRegistry.registerType(new StringPlaceholderType());
    placeholderTypesRegistry.registerType(new ComponentLikePlaceholderType());
  }
  
  public MiniLocaleVelocity(ProxyServer proxyServer) {
    this.proxyServer = proxyServer;
  }

  @Override
  public Component legacySection(String message) {
    return LegacyComponentSerializer.legacySection().deserialize(message);
  }

  @Override
  public String getLanguage(Audience receiver) {
    return receiver instanceof Player player ?
       player.getEffectiveLocale() != null ? player.getEffectiveLocale().getLanguage() : null
       : null;
  }

  @Override
  public void send(Message message, MessageDirection direction, Iterable<? extends Audience> audiences, Placeholders placeholders) {
    for (Audience audience : audiences) {
      audience.forEachAudience(item -> {
        direction.send(item, message.get(placeholders, item));
      });
    }
  }

  @Override
  public void showTitle(Audience audience, Component title, Component subtitle, int fadeIn, int stay, int fadeOut) {
    audience.showTitle(Title.title(title == null ? Component.empty() : title,
                                   subtitle == null ? Component.empty() : subtitle,
                                   Title.Times.times(Duration.ofMillis(fadeIn),
                                                     Duration.ofMillis(stay),
                                                     Duration.ofMillis(fadeOut))));
  }

  @Override
  public boolean isPlayer(Audience audience) {
    return audience instanceof Player;
  }

  @Override
  public Audience getOnlinePlayer(UUID uuid) {
    return proxyServer.getPlayer(uuid).orElse(null);
  }

  @Override
  public Audience getPlayer(String name) {
    return proxyServer.getPlayer(name).orElse(null);
  }

  @Override
  public String plain(Component component) {
    return PlainTextComponentSerializer.plainText().serialize(component);
  }

  @Override
  public Audience getOnlinePlayers() {
    return Audience.audience(proxyServer.getAllPlayers());
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
    return proxyServer.getConsoleCommandSource();
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
    return new PlaceholdersVelocity();
  }
  
  @Override
  public PlaceholderTypesRegistry placeholderTypesRegistry() {
    return placeholderTypesRegistry;
  }
}
