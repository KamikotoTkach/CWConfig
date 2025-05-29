package ru.cwcode.tkach.locale.paper.old.placeholders;

import net.kyori.adventure.text.minimessage.Template;
import ru.cwcode.tkach.locale.placeholders.PlaceholderType;

import java.time.temporal.TemporalAccessor;

public class DatePlaceholderType implements PlaceholderType<Template> {
  @Override
  public boolean isSupports(Object value) {
    return value instanceof TemporalAccessor;
  }
  
  @Override
  public Template convert(String key, Object value) {
    return Template.of(key.toLowerCase(), value.toString());
  }
}
