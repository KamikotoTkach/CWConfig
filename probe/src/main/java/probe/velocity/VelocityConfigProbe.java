package probe.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;
import probe.ExtrasCheck;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfig;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfigManager;
import ru.cwcode.tkach.config.velocityplatform.VelocityPluginConfigPlatform;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.Placeholder;
import ru.cwcode.tkach.locale.platform.MiniLocale;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

/**
 * Velocity side of the CWConfig acceptance probe (LIBS_MULTIPLATFORM.md 2.3): console command {@code cfgprobe}.
 */
@Plugin(id = "configprobe", name = "ConfigProbe", version = "1.0", dependencies = {@Dependency(id = "cwconfig")})
public class VelocityConfigProbe {
  @Inject
  ProxyServer server;
  @Inject
  Logger logger;
  @Inject
  @DataDirectory
  Path dataDirectory;

  private int passed;
  private int failed;

  public static class ProbeConfig extends YmlConfig {
    public String text = "default";
    public int number = 5;
    public Message message = new Message("<green>Hello <name>");
  }

  @Subscribe
  public void onInitialize(ProxyInitializeEvent event) {
    server.getCommandManager().register(server.getCommandManager().metaBuilder("cfgprobe").build(),
                                        (SimpleCommand) invocation -> run());
  }

  private void run() {
    passed = 0;
    failed = 0;
    MiniLocale locale = MiniLocale.getInstance();
    logger.info("PROBE platform {} {}, MiniLocale={}", server.getVersion().getName(), server.getVersion().getVersion(),
                locale.getClass().getSimpleName());

    check("config: save then load with a fresh manager keeps every field", () -> {
      Files.deleteIfExists(dataDirectory.resolve("probe.yml"));
      YmlConfigManager manager = new YmlConfigManager(new VelocityPluginConfigPlatform(this, server, logger, dataDirectory));
      ProbeConfig config = manager.load("probe", ProbeConfig.class);
      config.text = "changed";
      config.number = 42;
      config.message = new Message("<blue>Changed <name>");
      manager.save(config, options -> options.async(false));

      ProbeConfig loaded = new YmlConfigManager(new VelocityPluginConfigPlatform(this, server, logger, dataDirectory))
        .load("probe", ProbeConfig.class);
      return loaded.text.equals("changed") && loaded.number == 42 && loaded.message.serialize().equals("<blue>Changed <name>");
    });

    check("locale: MiniMessage with placeholders", () -> {
      String plain = locale.plain(new Message("<red>Hi <name>, <count>").get(Placeholder.add("name", "Bob").add("count", 5)));
      logger.info("PROBE   plain={}", plain);
      return plain.equals("Hi Bob, 5");
    });

    check("locale: legacy section and ampersand", () -> new Message("<red>Hi").getLegacySection().equals("§cHi")
                                                          && locale.plain(locale.legacyAmpersand("&aGreen")).equals("Green"));

    check("locale: title, action bar and chat to console", () -> {
      Audience console = locale.console();
      locale.showTitle(console, Component.text("title"), Component.text("subtitle"), 100, 1000, 100);
      new Message("<gold>probe chat <name>").send(console, Placeholder.add("name", "Bob"));
      new Message("<gold>probe action bar").sendActionBar(console);
      return true;
    });

    check("locale: Message field with extras in yml plays them on send and keeps them on save", () -> {
      String result = ExtrasCheck.run(dataDirectory,
                                      () -> new YmlConfigManager(new VelocityPluginConfigPlatform(this, server, logger, dataDirectory)));
      logger.info("PROBE   extras {}", result);
      return ExtrasCheck.ok(result);
    });

    logger.info("PROBE RESULT passed={} failed={}", passed, failed);
  }

  private void check(String name, Callable<Boolean> body) {
    try {
      if (Boolean.TRUE.equals(body.call())) {
        passed++;
        logger.info("PROBE OK   {}", name);
      } else {
        failed++;
        logger.warn("PROBE FAIL {}", name);
      }
    } catch (Throwable t) {
      failed++;
      logger.warn("PROBE FAIL " + name, t);
    }
  }
}
