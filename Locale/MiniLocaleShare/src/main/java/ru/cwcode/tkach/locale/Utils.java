package ru.cwcode.tkach.locale;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import ru.cwcode.tkach.locale.platform.MiniLocale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Utils {
  
  private static @NotNull List<String> collectionToStringCollection(Collection<?> collection) {
    List<String> list = new ArrayList<>();
    
    for (Object o : collection) {
      if (o instanceof Component) {
        list.add(MiniLocale.getInstance().miniMessageWrapper().serialize((Component) o));
        continue;
      }
      
      list.add(LegacyToMiniMessageReplacer.replace(o.toString()));
    }
    
    return list;
  }
  
  private static List<String> expandLine(String line, String token, List<String> replacements) {
    int index = line.indexOf(token);
    if (index == -1) return List.of(line);
    
    String prefix = line.substring(0, index);
    String suffix = line.substring(index + token.length());
    
    List<String> result = new ArrayList<>(replacements.size());
    
    for (String replacement : replacements) {
      result.add(prefix + replacement + suffix);
    }
    
    return result;
  }
  
  public static List<String> replaceMultilinePlaceholders(List<String> original, Placeholders placeholders) {
    List<String> result = original;
    
    for (Map.Entry<String, Object> entry : placeholders.getRaw().entrySet()) {
      Object value = entry.getValue();
      if (!(value instanceof Collection<?>)) continue;
      
      String token = "<" + entry.getKey() + ">";
      List<String> replacements = collectionToStringCollection((Collection<?>) value);
      List<String> next = new ArrayList<>();
      boolean changed = false;
      
      for (String line : result) {
        if (!line.contains(token)) {
          next.add(line);
          continue;
        }
        
        changed = true;
        next.addAll(expandLine(line, token, replacements));
      }
      
      if (changed) {
        result = next;
      }
    }
    
    return result;
  }
}
