package ru.cwcode.tkach.locale.paper.old;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Template;
import ru.cwcode.cwutils.text.nanoid.NanoID;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.Placeholders;
import ru.cwcode.tkach.locale.platform.MiniLocale;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PlaceholdersOld implements Placeholders {
  private final HashMap<String, Object> resolvers = new HashMap<>();

  public PlaceholdersOld() {
  }

  public PlaceholdersOld(String key, String value) {
    add(key, value);
  }

  public PlaceholdersOld(Template resolver) {
    add(resolver);
  }

  @Override
  public PlaceholdersOld add(String key, String value) {
    resolvers.put(key, value);
    return this;
  }

  @Override
  public PlaceholdersOld add(Object template) {
    if (template instanceof Template t) {
      resolvers.put(NanoID.randomNanoId(), t);
    } else {
      add(NanoID.randomNanoId(), template.toString());
    }

    return this;
  }
  
  @Override
  public Placeholders add(String key, Message message) {
    resolvers.put(key, message.serialize());
    return this;
  }
  
  @Override
  public PlaceholdersOld add(String key, Component value) {
    resolvers.put(key, value);
    return this;
  }
  
  @Override
  public Template[] getResolvers() {
    Object[] resolvers = MiniLocale.getInstance().placeholderTypesRegistry().convert(this.resolvers);
    return Arrays.copyOf(resolvers, resolvers.length, Template[].class);
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
  
  @Override
  public PlaceholdersOld add(String key, double value) {
    return add(key, String.valueOf(value));
  }

  @Override
  public PlaceholdersOld add(String key, int value) {
    return add(key, String.valueOf(value));
  }

  @Override
  public PlaceholdersOld add(String key, float value) {
    return add(key, String.valueOf(value));
  }

  @Override
  public PlaceholdersOld add(String key, long value) {
    return add(key, String.valueOf(value));
  }

  @Override
  public PlaceholdersOld add(String key, boolean value) {
    return add(key, String.valueOf(value));
  }
  
  @Override
  public Placeholders add(String key, Object value) {
    resolvers.put(key, value);
    return this;
  }
}