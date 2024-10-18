package ru.cwcode.tkach.config.repository.yml;

import org.jetbrains.annotations.NotNull;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfigManager;
import ru.cwcode.tkach.config.repository.RepositoryManager;
import ru.cwcode.tkach.config.repository.RepositoryOptions;

public class YmlRepositoryManager implements RepositoryManager<YmlRepository<?, ?>> {
  YmlConfigManager ymlConfigManager;
  
  public YmlRepositoryManager(YmlConfigManager ymlConfigManager) {
    this.ymlConfigManager = ymlConfigManager;
  }
  
  @Override
  public <R extends YmlRepository<?, ?>> R getRepository(Class<R> repositoryClass) {
    String name = extractRepositoryName(repositoryClass);
    
    return repositoryClass.cast(ymlConfigManager.findConfig(name).orElseGet(() -> {
      return load(repositoryClass, name);
    }));
  }
  
  @Override
  public boolean reload(YmlRepository<?, ?> repository) {
    load(repository.getClass(), repository.name());
    return true;
  }
  
  private <R extends YmlRepository<?, ?>> @NotNull R load(Class<R> repositoryClass, String name) {
    R repository = ymlConfigManager.load(name, repositoryClass);
    repository.ymlRepositoryManager = this;
    return repository;
  }
  
  private static <R extends YmlRepository<?, ?>> @NotNull String extractRepositoryName(Class<R> repositoryClass) {
    String name = repositoryClass.getSimpleName();
    
    RepositoryOptions options = repositoryClass.getAnnotation(RepositoryOptions.class);
    if (options != null) {
      if (!options.name().isEmpty()) name = options.name();
    }
    return name;
  }
}
