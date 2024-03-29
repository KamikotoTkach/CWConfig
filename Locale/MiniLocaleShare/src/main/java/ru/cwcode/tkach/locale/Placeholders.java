package ru.cwcode.tkach.locale;

import net.kyori.adventure.text.Component;

public interface Placeholders {
  Placeholders add(String key, String value);
  
  Placeholders add(String key, double value);
  
  Placeholders add(String key, int value);
  
  Placeholders add(String key, float value);
  
  Placeholders add(String key, long value);
  
  Placeholders add(String key, boolean value);
  
  Placeholders add(Object tag);
  
  Placeholders add(String key, Component value);
  
  Object[] getResolvers();
}
