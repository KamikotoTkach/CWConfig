package ru.cwcode.tkach.locale.placeholders;

public interface PlaceholderType<T> {
  boolean isSupports(Object value);
  
  T convert(String key, Object value);
}
