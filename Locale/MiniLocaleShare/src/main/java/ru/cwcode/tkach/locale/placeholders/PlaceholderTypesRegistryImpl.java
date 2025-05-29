package ru.cwcode.tkach.locale.placeholders;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class PlaceholderTypesRegistryImpl implements PlaceholderTypesRegistry {
  List<PlaceholderType<?>> registeredTypes = new ArrayList<>();
  Map<Class<?>, PlaceholderType<?>> placeholderTypeByClassCache = new ConcurrentHashMap<>();
  
  @Override
  public void registerType(PlaceholderType<?> type) {
    registeredTypes.add(type);
  }
  
  @Override
  public @Nullable PlaceholderType<?> findMatchingType(Object value) {
    PlaceholderType<?> cached = placeholderTypeByClassCache.get(value.getClass());
    if (cached != null) return cached;
    
    for (PlaceholderType<?> registeredType : registeredTypes) {
      if (registeredType.isSupports(value)) {
        placeholderTypeByClassCache.put(value.getClass(), registeredType);
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
      } else {
        Logger.getLogger("MiniLocale").warning(String.format("Cannot convert placeholder %s with type %s (%s)",
                                                             entry.getKey(), entry.getValue().getClass().getSimpleName(), entry.getValue()));
      }
    }
    
    return parsed.toArray();
  }
}
