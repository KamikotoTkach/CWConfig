package ru.cwcode.tkach.locale.velocity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import ru.cwcode.tkach.locale.Placeholders;
import tkachgeek.tkachutils.text.nanoid.NanoID;

import java.util.HashMap;
import java.util.Locale;

public class PlaceholdersVelocity implements Placeholders {
  private final HashMap<String, TagResolver> resolvers = new HashMap<>();
  
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
    String keyLowerCase = key.toLowerCase();
    
    resolvers.put(keyLowerCase, TagResolver.resolver(keyLowerCase, Tag.preProcessParsed(value)));
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
    String keyLowerCase = key.toLowerCase(Locale.ROOT);
    
    resolvers.put(keyLowerCase, TagResolver.resolver(keyLowerCase, Tag.inserting(value)));
    return this;
  }
  
  @Override
  public TagResolver[] getResolvers() {
    return resolvers.values().toArray(new TagResolver[0]);
  }
}
