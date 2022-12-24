package tkachgeek.config.minilocale;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import tkachgeek.commands.command.arguments.executor.MessageReturn;

import java.util.UUID;

public class Message {
  String message;
  
  public Message() {
  }
  
  public Message(String message) {
    this.message = message;
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
  
  public Component get() {
    return MiniMessage.get().parse(message);
  }
  
  public Component get(Placeholders placeholders) {
    return MiniMessage.get().parse(message, placeholders.getTemplates());
  }
  
  public void throwback() throws MessageReturn {
    throw new MessageReturn(MiniMessage.get().parse(message));
  }
  
  public void throwback(Placeholders placeholders) throws MessageReturn {
    throw new MessageReturn(MiniMessage.get().parse(message, placeholders.getTemplates()));
  }
}
