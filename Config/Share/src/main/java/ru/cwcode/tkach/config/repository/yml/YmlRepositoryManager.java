package ru.cwcode.tkach.config.repository.yml;

import org.jetbrains.annotations.NotNull;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfig;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfigManager;
import ru.cwcode.tkach.config.repository.RepositoryManager;
import ru.cwcode.tkach.config.repository.RepositoryOptions;

import java.util.Optional;

public class YmlRepositoryManager implements RepositoryManager<YmlRepository<?, ?>> {
  YmlConfigManager ymlConfigManager;
  
  public YmlRepositoryManager(YmlConfigManager ymlConfigManager) {
    this.ymlConfigManager = ymlConfigManager;
  }
  
  @Override
  public <R extends YmlRepository<?, ?>> R getRepository(Class<R> repositoryClass) {
    return load(repositoryClass,false);
  }
  
  @Override
  public boolean reload(YmlRepository<?, ?> repository) {
    load(repository.getClass(), true);
    return true;
  }
  
  public <R extends YmlRepository<?, ?>> @NotNull R load(Class<R> repositoryClass, boolean force) {
    String name = extractRepositoryName(repositoryClass);
    
    if (!force) {
      Optional<YmlConfig> config = ymlConfigManager.findConfig(name);
      if (config.isPresent() && repositoryClass.isInstance(config.get())) return repositoryClass.cast(config.get());
    }
    
    return ymlConfigManager.load(name, repositoryClass);
  }
  
  private static <R extends YmlRepository<?, ?>> @NotNull String extractRepositoryName(Class<R> repositoryClass) {
    String name = repositoryClass.getSimpleName();
    
    RepositoryOptions options = repositoryClass.getAnnotation(RepositoryOptions.class);
    if (options != null && !options.name().isEmpty()) {
      name = options.name();
    }
    
    return name;
  }
}
