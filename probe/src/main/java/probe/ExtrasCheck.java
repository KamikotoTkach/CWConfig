package probe;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfig;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfigManager;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.Placeholder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Platform-neutral check shared by the Paper and Velocity probes: a plain {@code Message} field written as an object
 * with sound/title/extraDirections plays all of them on {@code send}, and saving keeps them.
 */
public final class ExtrasCheck {
  public static class ExtrasConfig extends YmlConfig {
    public Message reward = new Message("unset");
  }

  private static final String YML = """
    reward:
      message: "<green>Got <amount>"
      sound: entity.player.levelup
      title:
        title: "<gold>Reward <amount>"
      extraDirections:
        actionbar: "<gray>+<amount>"
    """;

  public static String run(Path dataFolder, Supplier<YmlConfigManager> managers) throws Exception {
    Path file = dataFolder.resolve("extras.yml");
    Files.createDirectories(dataFolder);
    Files.writeString(file, YML);

    YmlConfigManager manager = managers.get();
    ExtrasConfig config = manager.load("extras", ExtrasConfig.class);

    RecordingAudience audience = new RecordingAudience();
    config.reward.send(audience, Placeholder.add("amount", "5"));

    manager.save(config, options -> options.async(false));
    boolean kept = Files.readString(file).contains("entity.player.levelup");

    return "type=" + config.reward.getClass().getSimpleName() + " sounds=" + audience.sounds + " titles=" + audience.titles
           + " actionBars=" + audience.actionBars + " keptOnSave=" + kept;
  }

  public static boolean ok(String result) {
    return result.equals("type=ExtraMessage sounds=[entity.player.levelup] titles=1 actionBars=1 keptOnSave=true");
  }

  private static class RecordingAudience implements Audience {
    final List<String> sounds = new ArrayList<>();
    int titles;
    int actionBars;

    @Override
    public void playSound(Sound sound) {
      sounds.add(sound.name().value());
    }

    @Override
    public void showTitle(Title title) {
      titles++;
    }

    @Override
    public void sendActionBar(Component message) {
      actionBars++;
    }
  }
}
