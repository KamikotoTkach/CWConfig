package ru.cwcode.tkach.locale.paper.old.placeholders;

import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.inventory.ItemStack;
import ru.cwcode.cwutils.items.ItemStackUtils;
import ru.cwcode.tkach.locale.placeholders.PlaceholderType;

public class ItemStackPlaceholderType implements PlaceholderType<Template> {
  @Override
  public boolean isSupports(Object value) {
    return value instanceof ItemStack;
  }
  
  @Override
  public Template convert(String key, Object value) {
    return Template.of(key, (ItemStackUtils.getDisplayNameWithoutBrackets((ItemStack) value)));
  }
}
