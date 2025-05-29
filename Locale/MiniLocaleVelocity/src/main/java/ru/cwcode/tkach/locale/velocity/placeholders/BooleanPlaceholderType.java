package ru.cwcode.tkach.locale.velocity.placeholders;

import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import ru.cwcode.tkach.locale.placeholders.PlaceholderType;

public class BooleanPlaceholderType implements PlaceholderType<TagResolver> {
  @Override
  public boolean isSupports(Object value) {
    return value instanceof Boolean;
  }
  
  @Override
  public TagResolver convert(String key, Object value) {
    return Formatter.booleanChoice(key, (Boolean) value);
  }
}
