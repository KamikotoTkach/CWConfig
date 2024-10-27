package ru.cwcode.tkach.locale.velocity.placeholders;

import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import ru.cwcode.tkach.locale.placeholders.PlaceholderType;

public class ComponentLikePlaceholderType implements PlaceholderType<TagResolver> {
  @Override
  public boolean isSupports(Object value) {
    return value instanceof ComponentLike;
  }
  
  @Override
  public TagResolver convert(String key, Object value) {
    return TagResolver.resolver(key.toLowerCase(), Tag.inserting((ComponentLike) value));
  }
}
