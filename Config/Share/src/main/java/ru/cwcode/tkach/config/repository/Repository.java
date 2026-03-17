package ru.cwcode.tkach.config.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Repository<K, E extends RepositoryEntry<K>> extends Iterable<E> {
  @Nullable E getOrNull(K key);
  
  boolean put(K key, E entry);
  
  boolean remove(K key);
  
  Collection<E> list();
  
  Set<K> keys();
  
  @Override
  default @NotNull Iterator<E> iterator() {
    return list().iterator();
  }
  
  default Stream<E> stream() {
    return list().stream();
  }
  
  default Stream<E> parallelStream() {
    return list().parallelStream();
  }
  
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