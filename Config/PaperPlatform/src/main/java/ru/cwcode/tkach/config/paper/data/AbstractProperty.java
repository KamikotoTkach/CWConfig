package ru.cwcode.tkach.config.paper.data;

import java.util.function.BiConsumer;

public abstract class AbstractProperty<T, V> {
  private final BiConsumer<T, V> propertySetter;
  private final Class<V> type;
  
  protected AbstractProperty(Class<V> type, BiConsumer<T, V> propertySetter) {
    this.type = type;
    this.propertySetter = propertySetter;
  }
  
  public void apply(T object, Object value) {
    if (type.isInstance(value)) {
      propertySetter.accept(object, type.cast(value));
    }
  }
}
