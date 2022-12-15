package tkachgeek.config.yaml.module;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class ItemStackDeserializer extends JsonDeserializer<ItemStack> {
  @Override
  public ItemStack deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return ItemStack.deserializeBytes(p.getBinaryValue());
  }
}
