package tkachgeek.config.yaml.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class ItemStackSerializer extends JsonSerializer<ItemStack> {
  @Override
  public void serialize(ItemStack value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeBinary(value.serializeAsBytes());
  }
}
