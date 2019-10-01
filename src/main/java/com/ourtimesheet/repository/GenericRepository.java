package com.ourtimesheet.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by hassan on 3/14/16.
 */

public class GenericRepository<T> implements Repository<T> {

  private final MongoRepository<T, UUID> repository;

  public GenericRepository(MongoRepository<T, UUID> repository) {
    this.repository = repository;
  }

  @Override
  public <S extends T> S save(S entity) {
    return repository.save(entity);
  }

  @Override
  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<T> findById(UUID uuid) {
    return null;
  }

  @Override
  public boolean existsById(UUID uuid) {
    return false;
  }

  @Override
  public <S extends T> Iterable<S> saveWithoutAudit(Iterable<S> entities) {
    return repository.save(entities);
  }

  @Override
  public Iterable<T> findAll() {
    return repository.findAll();
  }

  @Override
  public Iterable<T> findAllById(Iterable<UUID> uuids) {
    return null;
  }


  @Override
  public long count() {
    return repository.count();
  }

  @Override
  public void deleteById(UUID uuid) {

  }

  @Override
  public void delete(T entity) {
    repository.delete(entity);
  }

  @Override
  public void deleteAll(Iterable<? extends T> entities) {

  }

  @Override
  public void deleteAll() {
    repository.deleteAll();
  }

  @Override
  public T find() {
    long numberOfRecords = count();
    if (numberOfRecords > 1) {
      throw new RepositoryException("More than one records found!");
    }
    if (numberOfRecords == 0) {
      return null;
    }
    return findAll().iterator().next();
  }

  @Override
  public List<T> findList() {
    return new ArrayList<>(repository.findAll());
  }
}
