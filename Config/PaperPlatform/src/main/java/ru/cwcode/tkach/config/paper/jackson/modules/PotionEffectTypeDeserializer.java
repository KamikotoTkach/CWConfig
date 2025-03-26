package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bukkit.potion.PotionEffectType;


import java.io.IOException;

public class PotionEffectTypeDeserializer extends JsonDeserializer<PotionEffectType> {
  
  @Override
  public PotionEffectType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return PotionEffectType.getByName(p.getValueAsString());
  }
}