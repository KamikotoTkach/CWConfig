package ru.cwcode.tkach.config.paper.jackson.modules.configurationSerializable;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class PotionEffectDeserializer extends ConfigurationSerializableDeserializer<PotionEffect> {
  public PotionEffectDeserializer() {
    super(PotionEffect.class);
  }
  
  @Override
  protected void preprocessMap(Map<String, Object> map) {
    String effect = (String) map.get("effect");
    map.put("effect", PotionEffectType.getByName(effect).getId());
  }
}
