package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.bukkit.inventory.ItemStack;
import ru.cwcode.cwutils.items.ItemStackUtils;
import ru.cwcode.tkach.config.annotation.Fancy;

import java.io.IOException;

public class ItemStackSerializer extends JsonSerializer<ItemStack> implements ContextualSerializer {
  boolean fancy;
  
  @Override
  public void serialize(ItemStack value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    if (fancy) {
      gen.writeString(ItemStackUtils.toSNBT(value));
    } else {
      gen.writeBinary(value.serializeAsBytes());
    }
  }
  
  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
    fancy = property != null && property.getAnnotation(Fancy.class) != null;
    return this;
  }
  
  @Override
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
    if (fancy) {
      visitor.expectStringFormat(typeHint);
    } else {
      visitor.expectAnyFormat(typeHint);
    }
  }
}