package ru.cwcode.tkach.locale.paper.old.placeholders;

import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import ru.cwcode.tkach.locale.placeholders.PlaceholderType;

public class PlayerPlaceholderType implements PlaceholderType<Template> {
  @Override
  public boolean isSupports(Object value) {
    return value instanceof OfflinePlayer;
  }
  
  @Override
  public Template convert(String key, Object value) {
    OfflinePlayer offlinePlayer = ((OfflinePlayer) value);
    Player onlinePlayer = offlinePlayer.getPlayer();
    
    if (onlinePlayer != null) {
      return Template.of(key, onlinePlayer.displayName());
    }
    
    return Template.of(key, offlinePlayer.getName());
  }
}
