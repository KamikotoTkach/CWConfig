package ru.cwcode.tkach.config.paper.jackson.modules;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import ru.cwcode.cwutils.items.ItemStackUtils;
import ru.cwcode.tkach.config.annotation.Fancy;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.core.JsonGenerator;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.BeanProperty;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.JsonSerializer;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.SerializerProvider;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;

public class PotionEffectTypeSerializer extends JsonSerializer<PotionEffectType> {
  
  @Override
  public void serialize(PotionEffectType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.getName());
  }
}