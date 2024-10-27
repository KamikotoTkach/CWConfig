package ru.cwcode.tkach.locale.velocity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import ru.cwcode.cwutils.text.nanoid.NanoID;
import ru.cwcode.tkach.locale.Placeholders;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PlaceholdersVelocity implements Placeholders {
  private final HashMap<String, Object> resolvers = new HashMap<>();

  public PlaceholdersVelocity() {
  }

  public PlaceholdersVelocity(String key, String value) {
    add(key, value);
  }

  public PlaceholdersVelocity(TagResolver resolver) {
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
    resolvers.put(key.toLowerCase(), value);
    return this;
  }
  
  @Override
  public Placeholders add(String key, Object value) {
    resolvers.put(key.toLowerCase(), value);
    return this;
  }
  
  @Override
  public Object[] getResolvers() {
    return resolvers.values().toArray();
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
