package ru.cwcode.tkach.config.repository.yml;

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
    String name = repositoryClass.getSimpleName();
    
    RepositoryOptions options = repositoryClass.getAnnotation(RepositoryOptions.class);
    if (options != null) {
      if (!options.name().isEmpty()) name = options.name();
    }
    
    return ymlConfigManager.load(name, repositoryClass);
  }
}
