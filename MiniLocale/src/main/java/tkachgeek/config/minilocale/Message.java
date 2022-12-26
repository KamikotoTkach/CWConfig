package tkachgeek.config.minilocale;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tkachgeek.commands.command.arguments.executor.MessageReturn;

import java.util.UUID;

import static net.kyori.adventure.text.minimessage.tag.resolver.TagResolver.resolver;

public class Message {
  String message;
  
  public Message() {
  }
  
  public Message(String message) {
    this.message = message;
  }
  public Message(String message, Mode mode) {
    this.message = message;

    switch (mode) {
      case MINI_MESSAGE:
        break;
      case LEGACY_AMPERSAND:
        this.message = MiniMessage.miniMessage().serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
        break;
      case LEGACY_SECTION:
        this.message = MiniMessage.miniMessage().serialize(LegacyComponentSerializer.legacySection().deserialize(message));
        break;
    }
  }
  
  public void send(Audience audience) {
    audience.sendMessage(get());
  }
  
  public void send(Audience audience, Placeholders placeholders) {
    audience.sendMessage(get(placeholders));
  }
  
  public void send(UUID maybeOfflinePlayer, Placeholders placeholders) {
    if (Bukkit.getOfflinePlayer(maybeOfflinePlayer).isOnline()) {
      Bukkit.getPlayer(maybeOfflinePlayer).sendMessage(get(placeholders));
    }
  }
  
  public void send(UUID maybeOfflinePlayer) {
    if (Bukkit.getOfflinePlayer(maybeOfflinePlayer).isOnline()) {
      Bukkit.getPlayer(maybeOfflinePlayer).sendMessage(get());
    }
  }
  
  public void send(String playerName, Placeholders placeholders) {
    Player player = Bukkit.getPlayer(playerName);
    if (player != null) {
      player.sendMessage(get(placeholders));
    }
  }
  
  public void send(String playerName) {
    Player player = Bukkit.getPlayer(playerName);
    if (player != null) {
      player.sendMessage(get());
    }
  }

  public String getText() {
    return LegacyComponentSerializer.legacySection().serialize(get());
  }
  
  public Component get() {
    return MiniMessage.miniMessage().deserialize(message);
  }

  public Component get(Placeholders placeholders) {
    return MiniMessage.miniMessage().deserialize(message, placeholders.getResolvers());
  }

  public void throwback() throws MessageReturn {
    throw new MessageReturn(MiniMessage.miniMessage().deserialize(message));
  }

  public void throwback(Placeholders placeholders) throws MessageReturn {
    throw new MessageReturn(MiniMessage.miniMessage().deserialize(message, placeholders.getResolvers()));
  }
}
