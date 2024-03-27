package ru.cwcode.tkach.locale;

import net.kyori.adventure.text.Component;
import ru.cwcode.tkach.locale.platform.MiniLocale;

public final class Placeholder {
  private Placeholder() {
    throw new RuntimeException();
  }
  
  public static Placeholders add(String key, String value) {
    return MiniLocale.getInstance().emptyPlaceholders().add(key, value);
  }
  
  public static Placeholders add(String key, double value) {
    return MiniLocale.getInstance().emptyPlaceholders().add(key, value);
  }
  
  public static Placeholders add(String key, int value) {
    return MiniLocale.getInstance().emptyPlaceholders().add(key, value);
  }
  
  public static Placeholders add(String key, float value) {
    return MiniLocale.getInstance().emptyPlaceholders().add(key, value);
  }
  
  public static Placeholders add(String key, long value) {
    return MiniLocale.getInstance().emptyPlaceholders().add(key, value);
  }
  
  public static Placeholders add(String key, boolean value) {
    return MiniLocale.getInstance().emptyPlaceholders().add(key, value);
  }
  
  public static Placeholders add(Object tag) {
    return MiniLocale.getInstance().emptyPlaceholders().add(tag);
  }
  
  public static Placeholders add(String key, Component value) {
    return MiniLocale.getInstance().emptyPlaceholders().add(key, value);
  }
}
