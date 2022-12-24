package tkachgeek.config.yaml.module;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.IOException;

public class LocationDeserializer extends JsonDeserializer<Location> {
  @Override
  public Location deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    /*JsonNode node = p.getCodec().readTree(p);
    
    double x = node.get("x").asDouble();
    double y = node.get("y").asDouble();
    double z = node.get("z").asDouble();
    float pitch = node.get("p").floatValue();
    float yaw = node.get("a").floatValue();
    String w = node.get("w").asText();
    
    return new Location(Bukkit.getWorld(w), x, y, z, yaw, pitch);*/
    
    String[] s = p.getValueAsString().split(", ");
    String world = s[0];
    
    String[] cords = s[1].split(" ");
    
    double x = Double.parseDouble(cords[0]);
    double y = Double.parseDouble(cords[1]);
    double z = Double.parseDouble(cords[2]);
    
    return new Location(Bukkit.getWorld(world), x, y, z);
  }
}
