package ru.cwcode.tkach.locale.paper.old.placeholders;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Template;
import ru.cwcode.tkach.locale.placeholders.PlaceholderType;
import ru.cwcode.tkach.locale.placeholders.UnparsedString;

public class UnparsedStringPlaceholderType implements PlaceholderType<Template> {
  @Override
  public boolean isSupports(Object value) {
    return value instanceof UnparsedString;
  }
  
  @Override
  public Template convert(String key, Object value) {
    return Template.of(key.toLowerCase(), Component.text(((UnparsedString) value).getString()));
  }
}
