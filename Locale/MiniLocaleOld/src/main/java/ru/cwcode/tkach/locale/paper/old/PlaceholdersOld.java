package ru.cwcode.tkach.locale.paper.old;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Template;
import ru.cwcode.cwutils.text.nanoid.NanoID;
import ru.cwcode.tkach.locale.Placeholders;

import java.util.HashMap;

public class PlaceholdersOld implements Placeholders {
  private final HashMap<String, Template> resolvers = new HashMap<>();

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
    resolvers.put(key, Template.of(key, value));
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
  public PlaceholdersOld add(String key, Component value) {
    resolvers.put(key, Template.of(key, value));
    return this;
  }

  @Override
  public Object[] getResolvers() {
    return resolvers.values().toArray();
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
}