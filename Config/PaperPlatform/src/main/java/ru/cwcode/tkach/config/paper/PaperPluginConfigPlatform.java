package ru.cwcode.tkach.config.paper;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import ru.cwcode.tkach.config.paper.jackson.modules.configurationSerializable.ConfigurationSerializableDeserializer;
import ru.cwcode.tkach.config.paper.jackson.modules.configurationSerializable.ConfigurationSerializableSerializer;
import ru.cwcode.tkach.config.paper.jackson.modules.configurationSerializable.PotionEffectDeserializer;
import ru.cwcode.tkach.config.paper.jackson.modules.configurationSerializable.PotionEffectSerializer;
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
  List<com.fasterxml.jackson.databind.Module> additionalJacksonModules = new ArrayList<>();
  
  public PaperPluginConfigPlatform(JavaPlugin plugin) {
    this.plugin = plugin;
    
    SimpleModule paperModule = new SimpleModule("cwconfig-paper");
    
    paperModule.addSerializer(ItemStack.class, new ItemStackSerializer());
    paperModule.addSerializer(PotionEffectType.class, new PotionEffectTypeSerializer());
    paperModule.addSerializer(OfflinePlayer.class, new OfflinePlayerSerializer());
    paperModule.addSerializer(Location.class, new LocationSerializer());
    paperModule.addSerializer(Vector.class, new VectorSerializer());
    paperModule.addSerializer(BlockVector.class, new BlockVectorSerializer());
    paperModule.addSerializer(PotionEffect.class, new PotionEffectSerializer());
    
    paperModule.addKeySerializer(PotionEffectType.class, new PotionEffectTypeSerializer());
    paperModule.addKeySerializer(OfflinePlayer.class, new OfflinePlayerSerializer());
    paperModule.addKeySerializer(Location.class, new LocationSerializer());
    paperModule.addKeySerializer(Vector.class, new VectorSerializer());
    paperModule.addKeySerializer(BlockVector.class, new BlockVectorSerializer());
    
    paperModule.addDeserializer(ItemStack.class, new ItemStackDeserializer());
    paperModule.addDeserializer(PotionEffectType.class, new PotionEffectTypeDeserializer());
    paperModule.addDeserializer(OfflinePlayer.class, new OfflinePlayerDeserializer());
    paperModule.addDeserializer(Location.class, new LocationDeserializer());
    paperModule.addDeserializer(Vector.class, new VectorDeserializer());
    paperModule.addDeserializer(BlockVector.class, new BlockVectorDeserializer());
    paperModule.addDeserializer(PotionEffect.class, new PotionEffectDeserializer());
    
    paperModule.addKeyDeserializer(PotionEffectType.class, new PotionEffectTypeKeyDeserializer());
    paperModule.addKeyDeserializer(OfflinePlayer.class, new OfflinePlayerKeyDeserializer());
    paperModule.addKeyDeserializer(Location.class, new LocationKeyDeserializer());
    paperModule.addKeyDeserializer(Vector.class, new VectorKeyDeserializer());
    paperModule.addKeyDeserializer(BlockVector.class, new BlockVectorKeyDeserializer());
    
    List.of(Color.class,
            FireworkEffect.class,
            Pattern.class,
            AttributeModifier.class,
            BoundingBox.class)
        .forEach(clazz -> {
          paperModule.addSerializer(clazz, new ConfigurationSerializableSerializer<>());
          paperModule.addDeserializer(clazz, new ConfigurationSerializableDeserializer<>((Class) clazz));
        });
    
    additionalJacksonModules.add(paperModule);
  }
  
  @Override
  public List<com.fasterxml.jackson.databind.Module> additionalJacksonModules() {
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
             .infinite()
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
  
  @Override
  public String name() {
    return plugin.getName();
  }
}
