package ru.cwcode.tkach.locale.paper.modern;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import ru.cwcode.cwutils.text.nanoid.NanoID;
import ru.cwcode.tkach.locale.Placeholders;

import java.util.HashMap;

public class PlaceholdersNew implements Placeholders {
  private final HashMap<String, TagResolver> resolvers = new HashMap<>();
  
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
    resolvers.put(key.toLowerCase(), TagResolver.resolver(key.toLowerCase(), Tag.preProcessParsed(value)));
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
    resolvers.put(NanoID.randomNanoId(), (TagResolver) tagResolver);
    return this;
  }
  
  @Override
  public Placeholders add(String key, Component value) {
    resolvers.put(key, TagResolver.resolver(key.toLowerCase(), Tag.inserting(value)));
    return this;
  }
  
  @Override
  public TagResolver[] getResolvers() {
    return resolvers.values().toArray(new TagResolver[0]);
  }
}
