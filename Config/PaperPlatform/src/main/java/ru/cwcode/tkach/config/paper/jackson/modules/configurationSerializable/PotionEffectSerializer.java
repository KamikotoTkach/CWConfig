package ru.cwcode.tkach.config.paper.jackson.modules.configurationSerializable;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.LinkedHashMap;
import java.util.Map;

public class PotionEffectSerializer extends ConfigurationSerializableSerializer<PotionEffect> {
  @Override
  protected Map<String, Object> preprocessMap(Map<String, Object> immutableMap) {
    LinkedHashMap<String, Object> map = new LinkedHashMap<>(immutableMap);
    
    map.put("effect", PotionEffectType.getById((Integer) map.get("effect")).getName());
    
    return map;
  }
}
