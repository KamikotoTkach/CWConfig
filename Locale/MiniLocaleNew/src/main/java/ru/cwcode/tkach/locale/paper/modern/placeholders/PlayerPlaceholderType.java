package ru.cwcode.tkach.locale.paper.modern.placeholders;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import ru.cwcode.tkach.locale.placeholders.PlaceholderType;

public class PlayerPlaceholderType implements PlaceholderType<TagResolver> {
  @Override
  public boolean isSupports(Object value) {
    return value instanceof OfflinePlayer;
  }
  
  @Override
  public TagResolver convert(String key, Object value) {
    OfflinePlayer offlinePlayer = ((OfflinePlayer) value);
    Player onlinePlayer = offlinePlayer.getPlayer();
    
    return TagResolver.resolver(key.toLowerCase(), onlinePlayer != null ? Tag.selfClosingInserting(onlinePlayer.displayName()) : Tag.selfClosingInserting(Component.text(offlinePlayer.getName())));
  }
}
