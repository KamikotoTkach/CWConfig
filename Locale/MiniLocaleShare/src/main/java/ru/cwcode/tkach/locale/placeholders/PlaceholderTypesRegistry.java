package ru.cwcode.tkach.locale.placeholders;

import java.util.Map;

public interface PlaceholderTypesRegistry {
  void registerType(PlaceholderType<?> type);
  
  PlaceholderType<?> findMatchingType(Object value);
  
  Object[] convert(Map<String, Object> rawPlaceholders);
}
