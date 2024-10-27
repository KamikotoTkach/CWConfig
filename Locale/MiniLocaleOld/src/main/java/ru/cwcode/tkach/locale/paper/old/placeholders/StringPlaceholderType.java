package ru.cwcode.tkach.locale.paper.old.placeholders;

import net.kyori.adventure.text.minimessage.Template;
import ru.cwcode.tkach.locale.placeholders.PlaceholderType;

public class StringPlaceholderType implements PlaceholderType<Template> {
  @Override
  public boolean isSupports(Object value) {
    return value instanceof String;
  }
  
  @Override
  public Template convert(String key, Object value) {
    return Template.of(key.toLowerCase(), (String) value);
  }
}
