package ru.cwcode.tkach.config.paper;

import org.bukkit.plugin.java.JavaPlugin;
import ru.cwcode.cwutils.server.ServerUtils;
import ru.cwcode.tkach.locale.paper.modern.MiniLocaleNew;
import ru.cwcode.tkach.locale.paper.old.MiniLocaleOld;
import ru.cwcode.tkach.locale.platform.MiniLocale;

public final class PaperPlatform extends JavaPlugin {
  @Override
  public void onEnable() {
    if (ServerUtils.isVersionGreater("1.17.1")) {
      MiniLocale.setInstance(new MiniLocaleNew());
    } else {
      MiniLocale.setInstance(new MiniLocaleOld());
    }
  }
}
