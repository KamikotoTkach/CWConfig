package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;

public class PotionEffectTypeKeyDeserializer extends KeyDeserializer {
  @Override
  public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
    return PotionEffectType.getByName(key);
  }
}
