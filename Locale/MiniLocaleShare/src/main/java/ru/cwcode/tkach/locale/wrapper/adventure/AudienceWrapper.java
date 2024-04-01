package ru.cwcode.tkach.locale.wrapper.adventure;

import net.kyori.adventure.audience.Audience;
import ru.cwcode.tkach.locale.platform.MiniLocale;

import java.util.*;

public class AudienceWrapper {
  public static Audience wrap(Collection<UUID> pretenders, UUID... anotherPretenders) {
    Set<Audience> players = new HashSet<>();

    addIfOnline(players, pretenders);
    addIfOnline(players, Arrays.asList(anotherPretenders));

    return Audience.audience(players);
  }

  public static Audience wrap(UUID... pretenders) {
    Set<Audience> players = new HashSet<>();
    addIfOnline(players, Arrays.asList(pretenders));
    return Audience.audience(players);
  }

  public static Audience wrap(String... pretenders) {
    Set<Audience> players = new HashSet<>();

    for (String pretender : pretenders) {
      Audience player = MiniLocale.getInstance().getPlayer(pretender);

      if (player != null) {
        players.add(player);
      }
    }

    return Audience.audience(players);
  }

  /**
   * @param players    сет игроков
   * @param pretenders итерируемый список с UUID игроков, который будут добавлены в players, если этот игрок онлайн
   */
  public static void addIfOnline(Set<Audience> players, Iterable<? extends UUID> pretenders) {
    for (UUID pretender : pretenders) {
      Audience player = MiniLocale.getInstance().getOnlinePlayer(pretender);
      if (player != null) {
        players.add(player);
      }
    }
  }

  public static Audience onlinePlayers() {
    return MiniLocale.getInstance().getOnlinePlayers();
  }
}
