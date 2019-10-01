package com.ourtimesheet.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by hassan on 5/1/16.
 */
public interface Repository<T> extends CrudRepository<T, UUID> {

  T find();

  List<T> findList();

  <S extends T> Iterable<S> saveWithoutAudit(Iterable<S> entities);
}