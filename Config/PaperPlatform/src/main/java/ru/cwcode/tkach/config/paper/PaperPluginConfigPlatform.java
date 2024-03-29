package ru.cwcode.tkach.config.paper;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.cwcode.cwutils.scheduler.Scheduler;
import ru.cwcode.tkach.config.base.ConfigPlatform;
import ru.cwcode.tkach.config.paper.jackson.modules.*;

import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PaperPluginConfigPlatform implements ConfigPlatform {
  JavaPlugin plugin;
  List<Module> additionalJacksonModules = new ArrayList<>();
  public PaperPluginConfigPlatform(JavaPlugin plugin) {
    this.plugin = plugin;
    
    SimpleModule paperModule = new SimpleModule("cwconfig-paper");
    
    paperModule.addSerializer(ItemStack.class, new ItemStackSerializer());
    paperModule.addSerializer(OfflinePlayer.class, new OfflinePlayerSerializer());
    paperModule.addSerializer(Location.class, new LocationSerializer());
    
    paperModule.addDeserializer(ItemStack.class, new ItemStackDeserializer());
    paperModule.addDeserializer(OfflinePlayer.class, new OfflinePlayerDeserializer());
    paperModule.addDeserializer(Location.class, new LocationDeserializer());
    
    additionalJacksonModules.add(paperModule);
  }
  
  @Override
  public List<Module> additionalJacksonModules() {
    return additionalJacksonModules;
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
