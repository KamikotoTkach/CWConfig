package ru.cwcode.tkach.locale.paper.old;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import ru.cwcode.tkach.locale.Placeholders;
import ru.cwcode.tkach.locale.Utils;
import ru.cwcode.tkach.locale.wrapper.adventure.MiniMessageWrapper;

import java.util.ArrayList;
import java.util.List;

public class MiniMessageWrapperOld implements MiniMessageWrapper {

  //todo: implement legacy parser to minimessage
  private static final MiniMessage mm = MiniMessage.get();

  @Override
  public String serialize(Component component) {
    return mm.serialize(component);
  }

  @Override
  public List<String> serialize(Component... component) {
    List<String> strings = new ArrayList<>();

    for (Component c : component) {
      strings.add(mm.serialize(c));
    }

    return strings;
  }

  @Override
  public List<String> serialize(List<Component> components) {
    List<String> strings = new ArrayList<>();

    for (Component c : components) {
      strings.add(mm.serialize(c));
    }

    return strings;
  }

  @Override
  public List<Component> deserialize(List<String> strings) {
    if (strings == null) return null;

    List<Component> components = new ArrayList<>();

    for (String s : strings) {
      components.add(mm.deserialize(replaceSection(s)));
    }

    return components;
  }

  @Override
  public List<Component> deserialize(String... string) {
    List<Component> components = new ArrayList<>();

    for (String s : string) {
      components.add(mm.deserialize(replaceSection(s)));
    }

    return components;
  }

  @Override
  public Component deserialize(String string) {
    if (string == null) return null;

    return mm.deserialize(replaceSection(string));
  }

  @Override
  public Component deserialize(String string, boolean disableItalic) {
    if (string == null) return null;

    Component deserialized = mm.deserialize(replaceSection(string));

    if (disableItalic) {
      return deserialized.decoration(TextDecoration.ITALIC, false);
    }

    return deserialized;
  }

  @Override
  public Component deserialize(String string, Placeholders placeholders) {
    if (string == null) return null;

    return mm.parse(replaceSection(string), placeholders.getResolvers());
  }

  @Override
  public Component deserialize(String string, Placeholders placeholders, boolean disableItalic) {
    if (string == null) return null;

    Component deserialized = mm.parse(replaceSection(string), placeholders.getResolvers());

    if (disableItalic) {
      return deserialized.decoration(TextDecoration.ITALIC, false);
    }

    return deserialized;
  }

  @Override
  public List<Component> deserialize(List<String> strings, Placeholders placeholders, boolean disableItalic) {
    if (strings == null) return null;
    strings = Utils.replaceMultilinePlaceholders(strings,placeholders);
    
    List<Component> components = new ArrayList<>();
    
    TextDecoration italicDecoration = TextDecoration.ITALIC;

    for (String s : strings) {
      Component deserialized = mm.parse(replaceSection(s), placeholders.getResolvers());

      if (disableItalic) {
        deserialized = deserialized.decoration(italicDecoration, false);
      }

      components.add(deserialized);
    }

    return components;
  }

  @NotNull
  private String replaceSection(String string) {
    return string.replace('§', '&');
  }
}
