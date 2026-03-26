package ru.cwcode.tkach.locale.paper.papi;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;

public class PapiProcessor {
  public static String process(String st, Audience receiver) {
    if (st == null) return null;
    
    Player player = receiver instanceof Player ? (Player) receiver : null;
    
    for (int pass = 0; pass < 2; pass++) {
      String processed = PlaceholderAPI.setPlaceholders(player, st);
      
      if (processed.equals(st)) {
        break;
      }
      
      st = processed;
    }
    
    return st;
  }
}
