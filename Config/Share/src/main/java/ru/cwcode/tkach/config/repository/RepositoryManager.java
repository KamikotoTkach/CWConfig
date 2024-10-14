package ru.cwcode.tkach.config.repository;

public interface RepositoryManager<RT extends Repository<?, ?>> {
  <R extends RT> R getRepository(Class<R> repositoryClass);
}
