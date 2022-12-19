package tkachgeek.config.minilocale;

import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class AudienceWrapper {
  public static Audience wrap(Collection<UUID> pretenders, UUID... anotherPretenders) {
    Set<Player> players = new HashSet<>();
    addIfOnline(players, pretenders);
    addIfOnline(players, Arrays.asList(anotherPretenders));
    return Audience.audience(players);
  }
  
  private static void addIfOnline(Set<Player> players, Iterable<? extends UUID> pretenders) {
    for (UUID pretender : pretenders) {
      if (Bukkit.getOfflinePlayer(pretender).isOnline()) {
        Player player = Bukkit.getPlayer(pretender);
        players.add(player);
      }
    }
  }
}
