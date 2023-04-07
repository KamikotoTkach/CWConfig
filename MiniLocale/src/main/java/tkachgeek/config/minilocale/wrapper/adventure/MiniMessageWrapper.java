package tkachgeek.config.minilocale.wrapper.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import tkachgeek.config.minilocale.Placeholders;

import java.util.ArrayList;
import java.util.List;

public class MiniMessageWrapper {
  public static Component deserialize(String string) {
    return MiniMessage.miniMessage().deserialize(string);
  }
  
  public static List<Component> deserialize(String... string) {
    List<Component> components = new ArrayList<>();
    
    for (String s : string) {
      components.add(MiniMessage.miniMessage().deserialize(s));
    }
    
    return components;
  }
  
  public static List<Component> deserialize(List<String> strings) {
    List<Component> components = new ArrayList<>();
    
    for (String s : strings) {
      components.add(MiniMessage.miniMessage().deserialize(s));
    }
    
    return components;
  }
  
  public static String serialize(Component component) {
    return MiniMessage.miniMessage().serialize(component);
  }
  
  public static List<String> serialize(Component... component) {
    List<String> strings = new ArrayList<>();
    
    for (Component c : component) {
      strings.add(MiniMessage.miniMessage().serialize(c));
    }
    
    return strings;
  }
  
  public static List<String> serialize(List<Component> components) {
    List<String> strings = new ArrayList<>();
    
    for (Component c : components) {
      strings.add(MiniMessage.miniMessage().serialize(c));
    }
    
    return strings;
  }
  
  public static Component deserialize(String string, Placeholders placeholders) {
    return MiniMessage.miniMessage().deserialize(string, placeholders.getResolvers());
  }
  
  public static Component deserialize(String string, boolean disableItalic) {
    Component deserialized = MiniMessage.miniMessage().deserialize(string);
    
    if (disableItalic) {
      return deserialized.decoration(TextDecoration.ITALIC, false);
    }
    
    return deserialized;
  }
  
  public static Component deserialize(String string, Placeholders placeholders, boolean disableItalic) {
    Component deserialized = MiniMessage.miniMessage().deserialize(string, placeholders.getResolvers());
    
    if (disableItalic) {
      return deserialized.decoration(TextDecoration.ITALIC, false);
    }
    
    return deserialized;
  }
  
  public static List<Component> deserialize(List<String> strings, Placeholders placeholders, boolean disableItalic) {
    List<Component> components = new ArrayList<>();
    MiniMessage miniMessage = MiniMessage.miniMessage();
    TextDecoration italicDecoration = TextDecoration.ITALIC;
    
    for (String s : strings) {
      Component deserialized = miniMessage.deserialize(s, placeholders.getResolvers());
      
      if (disableItalic) {
        deserialized = deserialized.decoration(italicDecoration, false);
      }
      
      components.add(deserialized);
    }
    
    return components;
  }
}
