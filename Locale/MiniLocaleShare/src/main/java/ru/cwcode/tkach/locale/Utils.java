package ru.cwcode.tkach.locale;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Utils {
  
  private static @NotNull List<String> collectionToStringCollection(Collection<?> collection) {
    List<String> list = new ArrayList<>();
    
    for (Object o : collection) {
      list.add(o.toString());
    }
    
    return list;
  }
  
  public static List<String> replaceMultilinePlaceholders(List<String> original, Placeholders placeholders) {
    List<String> copyIfNecessary = null;
    
    for (Map.Entry<String, Object> entry : placeholders.getRaw().entrySet()) {
      if (!(entry.getValue() instanceof Collection<?>)) continue;
      
      int index = original.indexOf("<" + entry.getKey() + ">");
      if (index == -1) continue;
      
      if (copyIfNecessary == null) copyIfNecessary = new ArrayList<>(original);
      
      copyIfNecessary.remove(index);
      copyIfNecessary.addAll(index, collectionToStringCollection((Collection<?>) entry.getValue()));
    }
    
    return copyIfNecessary == null ? original : copyIfNecessary;
  }
}
