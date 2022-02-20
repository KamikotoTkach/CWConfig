package tkachgeek.yaml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import tkachgeek.core.Config;
import tkachgeek.core.Reloadable;
import tkachgeek.core.Utils;
import tkachgeek.yaml.module.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YmlConfigManager {
  static public HashMap<String, Config> configs = new HashMap<>();
  static JavaPlugin plugin;
  static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
  
  public static void init(JavaPlugin plugin) {
    YmlConfigManager.plugin = plugin;
    
    mapper.findAndRegisterModules();
    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    
    SimpleModule module = new SimpleModule("TkacGeekModules");
    
    module.addDeserializer(Enum.class, new EnumDeserializer());
    module.addSerializer(Enum.class, new EnumSerializer());
    
    module.addDeserializer(Location.class, new LocationDeserializer());
    module.addSerializer(Location.class, new LocationSerializer());
    
    module.addDeserializer(ItemStack.class, new ItemStackDeserializer());
    module.addSerializer(ItemStack.class, new ItemStackSerializer());
    
    YmlConfigManager.module(module);
  }
  
  public static void module(Module module) {
    mapper.registerModule(module);
  }
  
  public static <T extends YmlConfig> T load(String path, Class<T> type) {
    Logger.getLogger(plugin.getName()).log(Level.INFO, "");
    Logger.getLogger(plugin.getName()).log(Level.INFO, "Чтение конфига " + path + ".yml");
    
    T config = null;
    
    try {
      String yaml = Utils.readString(getPath(path));
      
      if (yaml.length() == 0) {
        config = Utils.getNewInstance(type);
      } else {
        config = mapper.readValue(yaml, type);
      }
    } catch (IOException e) {
      Logger.getLogger(plugin.getName()).log(Level.WARNING, "Не удалось прочесть конфиг " + path + ".yml");
      
      e.printStackTrace();
    }
    
    if (config == null) {
      Logger.getLogger(plugin.getName()).log(Level.WARNING, "Создание конфига " + path + ".yml");
      config = Utils.getNewInstance(type);
    }
    
    if (config == null) {
      Logger.getLogger(plugin.getName()).log(Level.WARNING, "Не удалось создать конфиг " + path + ".yml (" + type.getSimpleName() + ")");
    } else {
      config.path = path;
      configs.put(path, config);
      
      Logger.getLogger(plugin.getName()).log(Level.INFO, "Успешно загружен конфиг " + path + ".yml");
    }
    
    return config;
  }
  
  public static void store(String path, YmlConfig object) {
    StringWriter writer = new StringWriter();
    
    try {
      mapper.writeValue(writer, object);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    Utils.writeString(getPath(path), writer.toString());
  }
  
  static Path getPath(String path) {
    return Paths.get(plugin.getDataFolder().toString() + File.separatorChar + path + ".yml");
  }
  
  public static void storeAll() {
    for (Config config : configs.values()) {
      if (config.saveOnDisabling) {
        Logger.getLogger(plugin.getName()).log(Level.INFO, "");
        Logger.getLogger(plugin.getName()).log(Level.INFO, "Сохранение конфига " + config.path + ".yml");
        
        try {
          config.store();
        } catch (Exception e) {
          
          Logger.getLogger(plugin.getName()).log(Level.WARNING, "Ошибка при сохранении конфига" + config.path + ".yml");
          
          e.printStackTrace();
          continue;
        }
        
        Logger.getLogger(plugin.getName()).log(Level.INFO, "Конфиг " + config.path + ".yml сохранён");
      }
    }
  }
  
  public static void reload() {
    for (Config config : configs.values()) {
      if (config instanceof Reloadable) {
        
        Logger.getLogger(plugin.getName()).log(Level.INFO, "Перезагрузка конфига " + config.path + ".yml");
        
        try {
          ((Reloadable) config).reload();
        } catch (Exception e) {
          Logger.getLogger(plugin.getName()).log(Level.WARNING, "Перезагрузка конфига " + config.path + ".yml не удалась: " + e.getMessage());
          continue;
        }
        Logger.getLogger(plugin.getName()).log(Level.INFO, "Перезагрузка конфига " + config.path + ".yml успешно");
      }
    }
  }
  
  public static String toString(YmlConfig config) {
    StringWriter writer = new StringWriter();
    
    try {
      mapper.writeValue(writer, config);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return writer.toString();
  }
}
