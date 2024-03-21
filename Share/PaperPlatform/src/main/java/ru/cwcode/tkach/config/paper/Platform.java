package ru.cwcode.tkach.config.paper;

import org.bukkit.plugin.java.JavaPlugin;
import tkachgeek.tkachutils.scheduler.Scheduler;

import java.nio.file.Path;
import java.time.Duration;

public class Platform extends ru.cwcode.tkach.config.base.Platform {
  JavaPlugin plugin;
  
  public Platform(JavaPlugin plugin) {
    this.plugin = plugin;
  }
  
  @Override
  public void info(String message) {
    plugin.getLogger().info(message);
  }
  
  @Override
  public void warning(String message) {
    plugin.getLogger().warning(message);
  }
  
  @Override
  public void runAsync(Runnable runnable) {
    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
  }
  
  @Override
  public void schedule(Runnable runnable, Duration frequency, boolean async) {
    Scheduler.create()
             .async(async)
             .perform(runnable)
             .register(plugin, frequency.toMillis() / 50);
  }
  
  @Override
  public Path dataFolder() {
    return plugin.getDataFolder().toPath();
  }
  
  @Override
  public void disable() {
    plugin.getServer().getPluginManager().disablePlugin(plugin);
  }
}
