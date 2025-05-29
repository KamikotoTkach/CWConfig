package ru.cwcode.tkach.locale.paper.modern.placeholders;

import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;
import ru.cwcode.cwutils.items.ItemStackUtils;
import ru.cwcode.tkach.locale.placeholders.PlaceholderType;

public class ItemStackPlaceholderType implements PlaceholderType<TagResolver> {
  @Override
  public boolean isSupports(Object value) {
    return value instanceof ItemStack;
  }
  
  @Override
  public TagResolver convert(String key, Object value) {
    return TagResolver.resolver(key,Tag.selfClosingInserting((ItemStackUtils.getDisplayNameWithoutBrackets((ItemStack) value))));
  }
}
