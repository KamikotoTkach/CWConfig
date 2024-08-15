package ru.cwcode.tkach.config.jackson.yaml;

import ru.cwcode.tkach.config.base.Config;

import static ru.cwcode.tkach.config.server.ServerPlatform.l10n;

public abstract class YmlConfig extends Config<YmlConfig> {
  public String[] header() {
    return l10n.get("config.header.default").split("\n");
  }
}
