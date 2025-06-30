package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import org.bukkit.Location;

import java.io.IOException;

public class LocationSerializer extends JsonSerializer<Location> {
  public final boolean asField;
  
  public LocationSerializer(boolean asField) {
    this.asField = asField;
  }
  
  @Override
  public void serialize(Location value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    String builder =
      value.getWorld().getName() + ", " +
      value.getX() + " " +
      value.getY() + " " +
      value.getZ() + " " +
      value.getPitch() + " " +
      value.getYaw();
    
    if (asField) {
      gen.writeFieldName(builder);
    } else {
      gen.writeString(builder);
    }
  }
  
  @Override
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
    visitor.expectStringFormat(typeHint);
  }
}