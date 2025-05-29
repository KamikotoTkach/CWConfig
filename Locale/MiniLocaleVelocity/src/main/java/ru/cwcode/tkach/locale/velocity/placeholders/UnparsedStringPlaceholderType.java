package ru.cwcode.tkach.locale.velocity.placeholders;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import ru.cwcode.tkach.locale.placeholders.PlaceholderType;
import ru.cwcode.tkach.locale.placeholders.UnparsedString;

public class UnparsedStringPlaceholderType implements PlaceholderType<TagResolver> {
  @Override
  public boolean isSupports(Object value) {
    return value instanceof UnparsedString;
  }
  
  @Override
  public TagResolver convert(String key, Object value) {
    return TagResolver.resolver(key, Tag.selfClosingInserting(Component.text(((UnparsedString) value).getString())));
  }
}
