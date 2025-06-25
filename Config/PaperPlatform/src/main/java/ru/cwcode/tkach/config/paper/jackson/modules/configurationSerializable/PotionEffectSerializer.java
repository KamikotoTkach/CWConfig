package ru.cwcode.tkach.config.paper.jackson.modules.configurationSerializable;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
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
  
  @Override
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
    JsonObjectFormatVisitor objectVisitor = visitor.expectObjectFormat(typeHint);
    
    objectVisitor.property("effect",
                           visitor.getProvider().findValueSerializer(String.class),
                           typeHint);
    
    objectVisitor.property("duration",
                           visitor.getProvider().findValueSerializer(Integer.class),
                           typeHint);
    
    objectVisitor.property("amplifier",
                           visitor.getProvider().findValueSerializer(Integer.class),
                           typeHint);
    
    objectVisitor.property("ambient",
                           visitor.getProvider().findValueSerializer(Boolean.class),
                           typeHint);
    
    objectVisitor.property("has-particles",
                           visitor.getProvider().findValueSerializer(Boolean.class),
                           typeHint);
    
    objectVisitor.property("has-icon",
                           visitor.getProvider().findValueSerializer(Boolean.class),
                           typeHint);
  }
}
