package ru.cwcode.tkach.config.velocityplatform;

import com.fasterxml.jackson.databind.Module;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import ru.cwcode.tkach.config.base.ConfigPlatform;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VelocityPluginConfigPlatform implements ConfigPlatform {
  private final Object plugin;
  private final ProxyServer server;
  private final Logger logger;
  private final Path dataDirectory;
  
  public VelocityPluginConfigPlatform(Object plugin, ProxyServer server, Logger logger, Path dataDirectory) {
    this.plugin = plugin;
    this.server = server;
    this.logger = logger;
    this.dataDirectory = dataDirectory;
  }
  
  @Override
  public List<Module> additionalJacksonModules() {
    return Collections.emptyList();
  }
  
  @Override
  public void info(String message) {
    logger.info(message);
  }
  
  @Override
  public void warning(String message) {
    logger.warn(message);
  }
  
  @Override
  public void runAsync(Runnable runnable) {
    server.getScheduler()
          .buildTask(plugin, runnable)
          .delay(0, TimeUnit.MICROSECONDS)
          .schedule();
  }
  
  @Override
  public void schedule(Runnable runnable, Duration frequency, boolean async) {
    server.getScheduler()
          .buildTask(plugin, runnable)
          .repeat(frequency)
          .schedule();
  }
  
  @Override
  public Path dataFolder() {
    return dataDirectory;
  }
  
  @Override
  public void disable() {
    //у велосити нет возможности отгрузить плагин
    logger.error("Произошла критическая ошибка, плагин не может продолжать свою корректную работу.");
  }
}
