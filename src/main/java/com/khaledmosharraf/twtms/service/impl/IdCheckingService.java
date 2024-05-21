package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.exception.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.ParameterizedType;

public class IdCheckingService<Model, IdType> {

  private final CrudRepository<Model, IdType> repository;

  public IdCheckingService(CrudRepository<Model, IdType> repository) {
    this.repository = repository;
  }

  protected Model getIfExistById(IdType id) {
    String entity =
        ((Class<Model>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0])
            .getSimpleName();
    return repository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("entity.idnotpresent", entity, id));
  }
}
