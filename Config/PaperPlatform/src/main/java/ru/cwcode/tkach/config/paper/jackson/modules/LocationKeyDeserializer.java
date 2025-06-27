package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationKeyDeserializer extends KeyDeserializer {
  @Override
  public Object deserializeKey(String key, DeserializationContext ctxt) {
    String[] s = key.split(", ");
    String world = s[0];
    
    String[] cords = s[1].split(" ");
    
    double x = Double.parseDouble(cords[0]);
    double y = Double.parseDouble(cords[1]);
    double z = Double.parseDouble(cords[2]);
    
    if (cords.length > 3) {
      float pitch = Float.parseFloat(cords[3]);
      float yaw = Float.parseFloat(cords[4]);
      return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    } else {
      return new Location(Bukkit.getWorld(world), x, y, z);
    }
  }
}