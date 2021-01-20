package com.knu.ynortman.repository;

import org.springframework.data.repository.CrudRepository;

import com.knu.ynortman.entity.CatalogEntry;

public interface CatalogEntryRepository extends CrudRepository<CatalogEntry, Long> {

}
