package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import org.bukkit.util.Vector;

import java.io.IOException;

public class VectorSerializer extends JsonSerializer<Vector> {
  @Override
  public void serialize(Vector value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    String builder =
      value.getX() + " " +
      value.getY() + " " +
      value.getZ() + " ";
    
    gen.writeString(builder);
  }
  
  @Override
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
    visitor.expectStringFormat(typeHint);
  }
}