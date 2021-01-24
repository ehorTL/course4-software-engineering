package com.knu.ynortman.service;

import com.knu.ynortman.dto.GetCatalogEntryDTO;
import com.knu.ynortman.dto.PostCatalogEntryDTO;;

public interface CatalogEntryService {
	Iterable<GetCatalogEntryDTO> getAllCatalogEntrys();
	GetCatalogEntryDTO getCatalogEntry(long id);
	GetCatalogEntryDTO addCatalogEntry(PostCatalogEntryDTO ct);
	GetCatalogEntryDTO updateCatalogEntryController(PostCatalogEntryDTO ct, long id);
	GetCatalogEntryDTO updateCatalogEntry(GetCatalogEntryDTO ct, long id);
	void deleteCatalogEntry(long id);
}
