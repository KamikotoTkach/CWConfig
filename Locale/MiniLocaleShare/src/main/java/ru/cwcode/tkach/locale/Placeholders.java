package ru.cwcode.tkach.locale;

import net.kyori.adventure.text.Component;

import java.util.Map;

public interface Placeholders {
  Placeholders add(String key, String value);
  
  Placeholders unparsed(String key, String value);

  Placeholders add(String key, double value);

  Placeholders add(String key, int value);

  Placeholders add(String key, float value);

  Placeholders add(String key, long value);

  Placeholders add(String key, boolean value);
  
  @Deprecated
  Placeholders add(Object tag);

  Placeholders add(String key, Component value);
  
  Placeholders add(String key, Message message);
  
  Placeholders add(String key, Object value);

  Object[] getResolvers();
  
  Map<String, Object> getRaw();
  
  Placeholders merge(Placeholders other);
  
  Placeholders remove(String key);
  
  Placeholders copy();
}
