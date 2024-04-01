package ru.cwcode.tkach.locale.wrapper.adventure;

import net.kyori.adventure.text.Component;
import ru.cwcode.tkach.locale.Placeholders;

import java.util.List;

public interface MiniMessageWrapper {
  String serialize(Component component);

  List<String> serialize(Component... component);

  List<String> serialize(List<Component> components);

  List<Component> deserialize(List<String> strings);

  List<Component> deserialize(String... string);

  Component deserialize(String string);

  Component deserialize(String string, boolean disableItalic);

  Component deserialize(String string, Placeholders placeholders);

  Component deserialize(String string, Placeholders placeholders, boolean disableItalic);

  List<Component> deserialize(List<String> strings, Placeholders placeholders, boolean disableItalic);
}
