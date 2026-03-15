package ru.cwcode.tkach.locale;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import ru.cwcode.tkach.locale.platform.MiniLocale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Utils {
  
  private static @NotNull List<String> collectionToStringCollection(Collection<?> collection) {
    List<String> list = new ArrayList<>();
    
    for (Object o : collection) {
      if (o instanceof Component) {
        list.add(MiniLocale.getInstance().miniMessageWrapper().serialize(((Component) o)));
        continue;
      }
      
      list.add(LegacyToMiniMessageReplacer.replace(o.toString()));
    }
    
    return list;
  }
  
  public static List<String> replaceMultilinePlaceholders(List<String> original, Placeholders placeholders) {
    AtomicReference<List<String>> copyIfNecessary = new AtomicReference<>();
    
    placeholders.getRaw().forEach((placeholder, value) -> {
      if (!(value instanceof Collection<?>)) return;
      
      int index = original.indexOf("<" + placeholder + ">");
      if (index == -1) return;
      
      if (copyIfNecessary.get() == null) copyIfNecessary.set(new ArrayList<>(original));
      
      copyIfNecessary.get().remove(index);
      copyIfNecessary.get().addAll(index, collectionToStringCollection((Collection<?>) value));
    });
    
    return copyIfNecessary.get() == null ? original : copyIfNecessary.get();
  }
}
