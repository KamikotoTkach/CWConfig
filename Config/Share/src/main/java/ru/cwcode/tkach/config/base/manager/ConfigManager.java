package ru.cwcode.tkach.config.base.manager;

import org.jetbrains.annotations.NotNull;
import ru.cwcode.commands.api.Sender;
import ru.cwcode.tkach.config.Utils;
import ru.cwcode.tkach.config.annotation.Reloadable;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;
import ru.cwcode.tkach.config.base.ConfigPlatform;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class ConfigManager<C extends Config<C>> {
  protected final ConfigPlatform platform;

  protected final ConfigLoader<C> loader; //file->str
  protected final ConfigPersister<C> persister; //str->file
  protected final ConfigMapper<C> mapper; //str > obj, obj>str
  protected final ConfigCreator<C> creator; //null -> init

  protected HashMap<String, C> configs = new HashMap<>();

  public ConfigManager(ConfigPlatform configPlatform,
                       ConfigLoader<C> loader,
                       ConfigPersister<C> persister,
                       ConfigMapper<C> mapper,
                       ConfigCreator<C> creator) {
    //
    this.platform = configPlatform;
    this.loader = loader;
    this.persister = persister;
    this.mapper = mapper;
    this.creator = creator;

    loader.setConfigManager(this);
    persister.setConfigManager(this);
    mapper.setConfigManager(this);
    creator.setConfigManager(this);
  }

  @NotNull
  private static ConfigPersistOptions loadOptions(Consumer<ConfigPersistOptions> options) {
    ConfigPersistOptions persistOptions = new ConfigPersistOptions();
    options.accept(persistOptions);
    return persistOptions;
  }

  public ConfigLoader<C> loader() {
    return loader;
  }

  public ConfigPersister<C> persister() {
    return persister;
  }

  public ConfigMapper<C> mapper() {
    return mapper;
  }

  public ConfigPlatform platform() {
    return platform;
  }

  public <V extends C> V load(String name, Class<V> configClass) {
    return load(name, configClass, ConfigPersistOptions.DEFAULT);
  }

  public <V extends C> V load(String name, Class<V> configClass, Consumer<ConfigPersistOptions> options) {
    return load(name, configClass, loadOptions(options));
  }

  public <V extends C> V load(String name, Class<V> configClass, ConfigPersistOptions options) {
    AtomicBoolean shouldSave = new AtomicBoolean(false);

    Optional<String> data = loader.load(name, options);
    Optional<V> config = data.flatMap(x -> mapper.map(x, configClass, options));

    if (config.isEmpty()) {
      shouldSave.set(true);

      if (data.isPresent()) backupConfigFile(name, options);

      config = creator.create(name, configClass, options);
    }

    config.ifPresentOrElse(c -> {
      configs.put(name, c);
      c.setName(name);
      c.setManager(this);

      if (!options.isSilent()) {
        platform.info("Успешно загружен конфиг %s".formatted(name));
      }

      if (shouldSave.get()) {
        save(c, options);
      }
    }, () -> {
      if (!options.isSilent()) {
        platform.info("Не удалось загрузить или создать конфиг %s, плагин будет отключён".formatted(name));
      }
      platform.disable();
    });

    return config.orElse(null);
  }

  public void backupConfigFile(String name, Consumer<ConfigPersistOptions> options) {
    backupConfigFile(name, loadOptions(options));
  }

  public void backupConfigFile(String name, ConfigPersistOptions options) {
    Path original = getPath(name);
    Path backup = getPath(name + " " + new Timestamp(System.currentTimeMillis()).toString().replace(":", "-"));

    if (!options.isSilent()) {
      platform.warning("Cоздание копии конфига %s (%s)".formatted(name, backup));
    }

    Utils.copy(original, backup);
  }

  public <V extends C> void save(V config) {
    save(config, __ -> {});
  }

  public void save(C config, Consumer<ConfigPersistOptions> options) {
    save(config, loadOptions(options));
  }

  public void save(C config, ConfigPersistOptions options) {
    if (config == null) return;

    mapper.map(config, options)
          .ifPresentOrElse(data -> {
            persister.persist(config, data, getPath(config.name()), options);
          }, () -> {
            if (!options.isSilent()) {
              platform.info("Не удалось замаппить конфиг %s в строку".formatted(config.name()));
            }
          });
  }

  public void saveAll() {
    saveAll(__ -> {});
  }

  public void saveAll(Consumer<ConfigPersistOptions> options) {
    saveAll(loadOptions(options));
  }

  public void saveAll(ConfigPersistOptions options) {
    if (options.isAsync()) {
      options.async(false); //не лучшая идея, но проблем пока что быть не должно

      platform.runAsync(() -> {
        saveAll0(options);
      });
    } else {
      saveAll0(options);
    }
  }

  public boolean reload(Sender sender, C config) {
    if (config instanceof Reloadable reloadable) {
      if (reloadable.reload()) {
        sender.sendMessage("Конфиг %s перезагружен".formatted(config.name()));
        return true;
      } else {
        sender.sendMessage("Не удалось перезагрузить конфиг %s".formatted(config.name()));
        return false;
      }
    }

    return false;
  }

  public List<C> reloadAll(Sender sender) {
    List<C> reloaded = new ArrayList<>();

    configs.forEach((name, config) -> {
      if (reload(sender, config)) {
        findConfig(config.name()).ifPresent(reloaded::add);
      }
    });

    return reloaded;
  }

  protected void saveAll0(ConfigPersistOptions options) {
    if (!options.isSilent()) {
      platform.info("Начато сохранение всех конфигов");
    }

    configs.values().forEach(config -> {
      if (config.saveAllEnabled()) save(config, options);
    });
  }

  public Path getPath(String name) {
    return platform.dataFolder().resolve(name + ".yml");
  }

  public <V extends C> String toString(V config) {
    return mapper.map(config, ConfigPersistOptions.DEFAULT).orElse(null);
  }

  public Set<String> getConfigNames(Predicate<C> predicate) {
    return configs.values().stream()
                  .filter(predicate)
                  .map(Config::name)
                  .collect(Collectors.toSet());
  }

  public void scheduleAutosave(Duration frequency, Consumer<ConfigPersistOptions> options) {
    platform.schedule(() -> saveAll(loadOptions(options)), frequency, loadOptions(options).isAsync());
  }

  public Optional<C> findConfig(String name) {
    return Optional.ofNullable(configs.get(name));
  }
}
