package ru.cwcode.tkach.config.paper.jackson.modules;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import ru.cwcode.cwutils.items.ItemStackUtils;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.core.JsonParser;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.core.JsonToken;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.DeserializationContext;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Base64;

public class PotionEffectTypeDeserializer extends JsonDeserializer<PotionEffectType> {
  
  @Override
  public PotionEffectType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return PotionEffectType.getByName(p.getValueAsString());
  }
}