package ru.cwcode.tkach.config.velocityplatform;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import ru.cwcode.tkach.locale.platform.MiniLocale;
import ru.cwcode.tkach.locale.velocity.MiniLocaleVelocity;

@Plugin(
   id = "cwconfig",
   name = "CWConfig",
   version = "2.1.1",
   dependencies = {@Dependency(id = "cwcommands")}
)
public class VelocityPlatform {
  @Inject
  ProxyServer proxyServer;
  
  @Subscribe
  public void initialize(ProxyInitializeEvent event) {
    MiniLocale.setInstance(new MiniLocaleVelocity(proxyServer));
  }
}
