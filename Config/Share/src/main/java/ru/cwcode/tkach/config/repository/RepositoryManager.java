package ru.cwcode.tkach.config.repository;

import ru.cwcode.tkach.config.repository.yml.YmlRepository;

public interface RepositoryManager<RT extends Repository<?, ?>> {
  <R extends RT> R getRepository(Class<R> repositoryClass);
  
  boolean reload(YmlRepository<?, ?> repository);
}
