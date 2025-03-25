package ru.cwcode.tkach.config.repository.yml;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.jetbrains.annotations.Nullable;
import ru.cwcode.tkach.config.annotation.Reloadable;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfig;
import ru.cwcode.tkach.config.repository.Repository;
import ru.cwcode.tkach.config.repository.RepositoryEntry;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static ru.cwcode.tkach.config.server.ServerPlatform.l10n;

public class YmlRepository<K, E extends RepositoryEntry<K>> extends YmlConfig implements Repository<K, E>, Reloadable {
  LinkedHashMap<K, E> entries = new LinkedHashMap<>();
  
  @Override
  public @Nullable E getOrNull(K key) {
    return entries.get(key);
  }
  
  @Override
  public boolean put(K key, E entry) {
    return entries.put(key, entry) != null;
  }
  
  @Override
  public boolean remove(K key) {
    return entries.remove(key) != null;
  }
  
  @Override
  public Collection<E> list() {
    return entries.values();
  }
  
  @Override
  public Set<K> keys() {
    return entries.keySet();
  }
  
  @Override
  public E computeIfAbsent(K key, Function<? super K, ? extends E> mappingFunction) {
    return entries.computeIfAbsent(key, mappingFunction);
  }
  
  @Override
  public boolean reload() {
    return manager.load(name(), getClass()) != null;
  }
  
  @JsonGetter("entries")
  private List<E> serialize() {
    return List.copyOf(entries.values());
  }
  
  @JsonSetter("entries")
  private void deserialize(List<E> entries) {
    this.entries.clear();
    
    for (E entry : entries) {
      if (this.entries.put(entry.getKey(), entry) != null) {
        manager.platform().warning(l10n.get("repository.duplicate", name(), entry.getKey()));
      }
    }
  }
}
