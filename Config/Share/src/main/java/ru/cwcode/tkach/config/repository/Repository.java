package ru.cwcode.tkach.config.repository;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Repository<K, E extends RepositoryEntry<K>> {
  @Nullable E getOrNull(K key);
  
  boolean put(K key, E entry);
  
  boolean remove(K key);
  
  Collection<E> list();
  
  default List<E> copyOf() {
    return List.copyOf(list());
  }
  
  default boolean removeEntry(E entry) {
    return remove(entry.getKey());
  }
  
  default boolean put(E entry) {
    return put(entry.getKey(), entry);
  }
  
  default Optional<E> get(K key) {
    return Optional.ofNullable(getOrNull(key));
  }
  
  default Collection<E> copy() {
    return new ArrayList<>(this.list());
  }
  
  default E computeIfAbsent(K key, Function<? super K, ? extends E> mappingFunction) {
    E val = getOrNull(key);
    
    if (val == null) {
      val = mappingFunction.apply(key);
      put(key, val);
    }
    
    return val;
  }
  
  default E getOrDefault(K key, E defaultValue) {
    E val = getOrNull(key);
    
    return val == null ? defaultValue : val;
  }
}
