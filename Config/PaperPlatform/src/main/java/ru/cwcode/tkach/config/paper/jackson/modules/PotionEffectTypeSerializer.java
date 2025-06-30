package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;

public class PotionEffectTypeSerializer extends JsonSerializer<PotionEffectType> {
  public final boolean asField;
  
  public PotionEffectTypeSerializer(boolean asField) {
    this.asField = asField;
  }
  
  @Override
  public void serialize(PotionEffectType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    String name = value.getName();
    
    if (asField) {
      gen.writeFieldName(name);
    } else {
      gen.writeString(name);
    }
  }
  
  @Override
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
    visitor.expectStringFormat(typeHint);
  }
}