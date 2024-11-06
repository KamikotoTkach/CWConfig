package ru.cwcode.tkach.config.paper;

import org.bukkit.plugin.java.JavaPlugin;
import ru.cwcode.cwutils.l10n.L10n;
import ru.cwcode.cwutils.l10n.PaperL10nPlatform;
import ru.cwcode.cwutils.server.PaperServerUtils;
import ru.cwcode.cwutils.server.ServerUtils;
import ru.cwcode.tkach.config.server.ServerPlatform;
import ru.cwcode.tkach.locale.paper.modern.MiniLocaleNew;
import ru.cwcode.tkach.locale.paper.old.MiniLocaleOld;
import ru.cwcode.tkach.locale.platform.MiniLocale;

public final class PaperPlatform extends JavaPlugin {
  @Override
  public void onLoad() {
    ServerPlatform.l10n = new L10n(new PaperL10nPlatform(this, this.getFile()));
    
    if (PaperServerUtils.isVersionGreater("1.17.1")) {
      MiniLocale.setInstance(new MiniLocaleNew());
    } else {
      MiniLocale.setInstance(new MiniLocaleOld());
    }
  }
}
