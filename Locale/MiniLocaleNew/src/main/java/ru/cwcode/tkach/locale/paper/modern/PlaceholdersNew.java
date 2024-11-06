package ru.cwcode.tkach.locale.paper.modern;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import ru.cwcode.cwutils.text.nanoid.NanoID;
import ru.cwcode.tkach.locale.Placeholders;
import ru.cwcode.tkach.locale.platform.MiniLocale;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PlaceholdersNew implements Placeholders {
  private final HashMap<String, Object> resolvers = new HashMap<>();

  public PlaceholdersNew() {
  }

  public PlaceholdersNew(String key, String value) {
    add(key, value);
  }

  public PlaceholdersNew(TagResolver resolver) {
    add(resolver);
  }

  @Override
  public Placeholders add(String key, String value) {
    resolvers.put(key.toLowerCase(), value);
    return this;
  }

  @Override
  public Placeholders add(String key, double value) {
    return add(key, String.valueOf(value));
  }

  @Override
  public Placeholders add(String key, int value) {
    return add(key, String.valueOf(value));
  }

  @Override
  public Placeholders add(String key, float value) {
    return add(key, String.valueOf(value));
  }

  @Override
  public Placeholders add(String key, long value) {
    return add(key, String.valueOf(value));
  }

  @Override
  public Placeholders add(String key, boolean value) {
    return add(key, String.valueOf(value));
  }

  @Override
  public Placeholders add(Object tagResolver) {
    resolvers.put(NanoID.randomNanoId(), tagResolver);
    return this;
  }

  @Override
  public Placeholders add(String key, Component value) {
    resolvers.put(key, value);
    return this;
  }
  
  @Override
  public Placeholders add(String key, Object value) {
    resolvers.put(key, value);
    return null;
  }
  
  @Override
  public TagResolver[] getResolvers() {
    Object[] resolvers = MiniLocale.getInstance().placeholderTypesRegistry().convert(this.resolvers);
    return Arrays.copyOf(resolvers, resolvers.length, TagResolver[].class);
  }
  
  @Override
  public Map<String, Object> getRaw() {
    return resolvers;
  }
  
  @Override
  public Placeholders merge(Placeholders other) {
    for (Map.Entry<String, Object> entry : other.getRaw().entrySet()) {
      resolvers.putIfAbsent(entry.getKey(), entry.getValue());
    }
    return this;
  }
}
