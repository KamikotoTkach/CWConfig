package ru.cwcode.tkach.locale.velocity.placeholders;

import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import ru.cwcode.tkach.locale.placeholders.PlaceholderType;

import java.time.temporal.TemporalAccessor;

public class DatePlaceholderType implements PlaceholderType<TagResolver> {
  @Override
  public boolean isSupports(Object value) {
    return value instanceof TemporalAccessor;
  }
  
  @Override
  public TagResolver convert(String key, Object value) {
    return Formatter.date(key, (TemporalAccessor) value);
  }
}
