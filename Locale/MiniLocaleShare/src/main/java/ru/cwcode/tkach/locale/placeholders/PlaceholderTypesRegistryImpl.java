package ru.cwcode.tkach.locale.placeholders;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlaceholderTypesRegistryImpl implements PlaceholderTypesRegistry {
  List<PlaceholderType<?>> registeredTypes = new ArrayList<>();
  
  @Override
  public void registerType(PlaceholderType<?> type) {
    registeredTypes.add(type);
  }
  
  @Override //todo: cache
  public @Nullable PlaceholderType<?> findMatchingType(Object value) {
    for (PlaceholderType<?> registeredType : registeredTypes) {
      if (registeredType.isSupports(value)) {
        return registeredType;
      }
    }
    
    return null;
  }
  
  @Override //todo: cache
  public Object[] convert(Map<String, Object> rawPlaceholders) {
    List<Object> parsed = new ArrayList<>(rawPlaceholders.size());
    
    for (Map.Entry<String, Object> entry : rawPlaceholders.entrySet()) {
      PlaceholderType<?> placeholderType = findMatchingType(entry.getValue());
      
      if (placeholderType != null) {
        parsed.add(placeholderType.convert(entry.getKey(), entry.getValue()));
      }
    }
    
    return parsed.toArray();
  }
}
