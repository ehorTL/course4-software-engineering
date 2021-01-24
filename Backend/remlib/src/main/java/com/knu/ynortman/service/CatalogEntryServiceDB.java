package com.knu.ynortman.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.knu.ynortman.dto.GetCatalogEntryDTO;
import com.knu.ynortman.dto.PostCatalogEntryDTO;
import com.knu.ynortman.entity.CatalogEntry;
import com.knu.ynortman.exception.ServerException;
import com.knu.ynortman.repository.CatalogEntryRepository;
import com.knu.ynortman.repository.StatusRepository;


@Service
public class CatalogEntryServiceDB implements CatalogEntryService {

	private final CatalogEntryRepository ctRepo;
	private final StatusRepository statusRepo;
	
	public CatalogEntryServiceDB(CatalogEntryRepository ctRepo, StatusRepository statusRepo) {
		this.ctRepo = ctRepo;
		this.statusRepo = statusRepo;
	}
	
	@Override
	public Iterable<GetCatalogEntryDTO> getAllCatalogEntrys() {
		List<GetCatalogEntryDTO> ctDTO = new LinkedList<>();
		try {
			ctRepo.findAll().forEach(new Consumer<CatalogEntry>() {
				@Override
				public void accept(CatalogEntry t) {
					ctDTO.add(GetCatalogEntryDTO.toDTO(t));
				}
			});
					
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
		return ctDTO;
	}

	@Override
	public GetCatalogEntryDTO getCatalogEntry(long id) {
		Optional<CatalogEntry> ct = null;
		try {
			ct = ctRepo.findById(id);
			if(ct.isPresent()) {
				return GetCatalogEntryDTO.toDTO(ct.get());
			}
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
		return null;
	}

	@Override
	public GetCatalogEntryDTO addCatalogEntry(PostCatalogEntryDTO ct) {
		try {
			CatalogEntry ctlgentry = PostCatalogEntryDTO.fromDTO(ct);
			ctlgentry.setAvlblFrom(new Date());
			ctlgentry.setCopiesAvlbl(ct.getCopiesNumber());
			ctlgentry.setStatus(statusRepo.findById(0).get());
			return GetCatalogEntryDTO.toDTO(ctRepo.save(ctlgentry));
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}

	@Override
	public GetCatalogEntryDTO updateCatalogEntryController(PostCatalogEntryDTO ct, long id) {
		try {
			Optional<CatalogEntry> oldCt = ctRepo.findById(id);
			CatalogEntry oldCatalog = null;
			if(oldCt.isEmpty()) {
				return null;
			} else {
				oldCatalog = oldCt.get();
				CatalogEntry newCatalog = PostCatalogEntryDTO.fromDTO(ct);
				oldCatalog.setLibrary(newCatalog.getLibrary());
				oldCatalog.setCopiesNumber(newCatalog.getCopiesNumber());
				oldCatalog.setLoanDays(newCatalog.getLoanDays());
			}
			return GetCatalogEntryDTO.toDTO(ctRepo.save(oldCatalog));
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}
	
	@Override
	public GetCatalogEntryDTO updateCatalogEntry(GetCatalogEntryDTO ct, long id) {
		try {
			Optional<CatalogEntry> oldCt = ctRepo.findById(id);
			if(oldCt.isEmpty()) {
				return null;
			} else {
				ct.setId(id);
			}
			return GetCatalogEntryDTO.toDTO(ctRepo.save(GetCatalogEntryDTO.fromDTO(ct)));
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}
	
	@Override
	public void deleteCatalogEntry(long id) {
		try {
			ctRepo.deleteById(id);
		} catch(Exception e) {
			throw new ServerException(e.getMessage());
		}

	}

}
