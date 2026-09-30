package probe;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfigManager;
import ru.cwcode.tkach.config.paper.PaperPluginConfigPlatform;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.Placeholder;
import ru.cwcode.tkach.locale.Placeholders;
import ru.cwcode.tkach.locale.platform.MiniLocale;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Acceptance probe for CWConfig (LIBS_MULTIPLATFORM.md 2.3): each check prints one PROBE line.
 * Run with the console command {@code cfgprobe}.
 */
public class ConfigProbe extends JavaPlugin {
  private int passed;
  private int failed;

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    passed = 0;
    failed = 0;

    MiniLocale locale = MiniLocale.getInstance();
    getLogger().info("PROBE platform " + Bukkit.getVersion() + ", MiniLocale=" + locale.getClass().getSimpleName());

    Path file = getDataFolder().toPath().resolve("probe.yml");
    check("config: defaults on first load, file written by save", () -> {
      Files.deleteIfExists(file);
      YmlConfigManager manager = new YmlConfigManager(new PaperPluginConfigPlatform(this));
      ProbeConfig config = manager.load("probe", ProbeConfig.class);
      manager.save(config, options -> options.async(false));
      return config.text.equals("default") && Files.exists(file);
    });

    check("config: save then load with a fresh manager keeps every field", () -> {
      YmlConfigManager manager = new YmlConfigManager(new PaperPluginConfigPlatform(this));
      ProbeConfig config = manager.load("probe", ProbeConfig.class);
      config.text = "changed";
      config.number = 42;
      config.item.setAmount(7);
      config.item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
      config.effect = PotionEffectType.JUMP;
      config.enchantment = Enchantment.PROTECTION_ENVIRONMENTAL;
      config.key = NamespacedKey.minecraft("changed");
      config.vector = new Vector(4, 5, 6);
      config.color = Color.fromRGB(40, 50, 60);
      config.message = new Message("<blue>Changed <name>");
      manager.save(config, options -> options.async(false));

      ProbeConfig loaded = new YmlConfigManager(new PaperPluginConfigPlatform(this)).load("probe", ProbeConfig.class);
      getLogger().info("PROBE   loaded item=" + loaded.item + " effect=" + loaded.effect.getName()
                       + " enchantment=" + loaded.enchantment.getKey() + " message=" + loaded.message.serialize());
      Map<String, Boolean> fields = new LinkedHashMap<>();
      fields.put("text", loaded.text.equals("changed"));
      fields.put("number", loaded.number == 42);
      fields.put("item", loaded.item.getAmount() == 7 && loaded.item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 3);
      fields.put("effect", loaded.effect.equals(PotionEffectType.JUMP));
      fields.put("enchantment", loaded.enchantment.equals(Enchantment.PROTECTION_ENVIRONMENTAL));
      fields.put("key", loaded.key.equals(NamespacedKey.minecraft("changed")));
      fields.put("vector", loaded.vector.equals(new Vector(4, 5, 6)));
      fields.put("color", loaded.color.equals(Color.fromRGB(40, 50, 60)));
      fields.put("message", loaded.message.serialize().equals("<blue>Changed <name>"));
      getLogger().info("PROBE   fields " + fields);
      return !fields.containsValue(false);
    });

    check("locale: MiniMessage with placeholders", () -> {
      Component component = new Message("<red>Hi <name>, <count>").get(Placeholder.add("name", "Bob").add("count", 5));
      String plain = locale.plain(component);
      getLogger().info("PROBE   plain=" + plain);
      return plain.equals("Hi Bob, 5");
    });

    check("locale: legacy section and ampersand", () -> {
      String section = new Message("<red>Hi").getLegacySection();
      Component ampersand = locale.legacyAmpersand("&aGreen");
      return section.equals("§cHi") && locale.plain(ampersand).equals("Green");
    });

    check("locale: title, action bar and chat to console", () -> {
      Audience console = locale.console();
      Placeholders placeholders = Placeholder.add("name", "Bob");
      locale.showTitle(console, Component.text("title"), Component.text("subtitle"), 100, 1000, 100);
      new Message("<gold>probe chat <name>").send(console, placeholders);
      new Message("<gold>probe action bar").sendActionBar(console);
      new Message("<gold>probe title").sendTitle(console);
      return true;
    });

    check("locale: ExtraMessage with sound and title", () -> {
      ProbeConfig config = new ProbeConfig();
      config.extra.send(locale.console(), Placeholder.add("name", "Bob"));
      return config.extra.getSound().getSound() != null;
    });

    check("locale: Message field with extras in yml plays them on send and keeps them on save", () -> {
      String result = ExtrasCheck.run(getDataFolder().toPath(), () -> new YmlConfigManager(new PaperPluginConfigPlatform(this)));
      getLogger().info("PROBE   extras " + result);
      return ExtrasCheck.ok(result);
    });

    if (Bukkit.getPluginManager().isPluginEnabled("CWConfigWebEditor")) {
      check("web editor: GET / answers 200", () -> {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:2025/").openConnection();
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);
        int code = connection.getResponseCode();
        int length = connection.getInputStream().readAllBytes().length;
        getLogger().info("PROBE   web editor code=" + code + " bytes=" + length);
        return code == 200 && length > 0;
      });
    } else {
      getLogger().info("PROBE web editor: SKIPPED (CWConfigWebEditor not enabled)");
    }

    getLogger().info("PROBE RESULT passed=" + passed + " failed=" + failed);
    return true;
  }

  private void check(String name, Callable<Boolean> body) {
    try {
      if (Boolean.TRUE.equals(body.call())) {
        passed++;
        getLogger().info("PROBE OK   " + name);
      } else {
        failed++;
        getLogger().warning("PROBE FAIL " + name);
      }
    } catch (Throwable t) {
      failed++;
      getLogger().warning("PROBE FAIL " + name + ": " + t);
      t.printStackTrace();
    }
  }
}
