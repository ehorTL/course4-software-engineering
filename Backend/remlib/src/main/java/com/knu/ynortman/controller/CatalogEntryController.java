package com.knu.ynortman.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knu.ynortman.dto.GetCatalogEntryDTO;
import com.knu.ynortman.dto.PostCatalogEntryDTO;
import com.knu.ynortman.exception.ApiError;
import com.knu.ynortman.exception.ServerException;
import com.knu.ynortman.service.CatalogEntryService;

@RestController
@RequestMapping(path = "/ctlgentry", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CatalogEntryController {
	private final CatalogEntryService ctService;

	public CatalogEntryController(CatalogEntryService ctService) {
		this.ctService = ctService;
	}

	@GetMapping
	public ResponseEntity<?> ctList() {
		try {
			List<GetCatalogEntryDTO> publications = (List<GetCatalogEntryDTO>) ctService.getAllCatalogEntrys();
			if (publications != null && publications.size() != 0) {
				return new ResponseEntity<Iterable<GetCatalogEntryDTO>>(publications, HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiError>(new ApiError(HttpStatus.NOT_FOUND, "There were no catalog entry found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> ctById(@PathVariable("id") Integer id) {
		try {
			GetCatalogEntryDTO ct =  ctService.getCatalogEntry(id);
			if (ct != null) {
				return new ResponseEntity<GetCatalogEntryDTO>(ct, HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiError>(new ApiError(HttpStatus.NOT_FOUND, "There were no book found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> postCt(@RequestBody PostCatalogEntryDTO ct) {
		try {
			return new ResponseEntity<GetCatalogEntryDTO>(ctService.addCatalogEntry(ct), HttpStatus.CREATED);
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putCt(@RequestBody PostCatalogEntryDTO ctentry, @PathVariable int id) {
		try {
			return new ResponseEntity<GetCatalogEntryDTO>(ctService.updateCatalogEntryController(ctentry, id), HttpStatus.OK);
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteCt(@PathVariable("id") Integer id) {
		try {
			ctService.deleteCatalogEntry(id);
			return new ResponseEntity<PostCatalogEntryDTO>(HttpStatus.OK);
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
