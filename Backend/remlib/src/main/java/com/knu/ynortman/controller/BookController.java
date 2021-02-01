package com.knu.ynortman.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.knu.ynortman.dto.PublicationDTO;
import com.knu.ynortman.exception.ApiError;
import com.knu.ynortman.exception.ServerException;
import com.knu.ynortman.service.CatalogEntryService;
import com.knu.ynortman.service.PublicationService;

@RestController
@RequestMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class BookController {

	private final PublicationService publicationService;
	private final CatalogEntryService ctService;

	public BookController(PublicationService publicationService, CatalogEntryService ctService) {
		this.publicationService = publicationService;
		this.ctService = ctService;
	}

	@GetMapping
	public ResponseEntity<?> bookList() {
		try {
			List<PublicationDTO> publications = (List<PublicationDTO>) publicationService.getAllPublications();
			if (publications != null && publications.size() != 0) {
				return new ResponseEntity<Iterable<PublicationDTO>>(publications, HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiError>(new ApiError(HttpStatus.NOT_FOUND, "There were no book found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> bookById(@PathVariable("id") Integer id) {
		try {
			PublicationDTO publication =  publicationService.getPublication(id);
			if (publication != null) {
				return new ResponseEntity<PublicationDTO>(publication, HttpStatus.OK);
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
	public ResponseEntity<?> postBook(@Valid @RequestBody PublicationDTO publication) {
		try {
			return new ResponseEntity<PublicationDTO>(publicationService.addPublication(publication), HttpStatus.CREATED);
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putPublication(@Valid @RequestBody PublicationDTO publication, @PathVariable int id) {
		try {
			return new ResponseEntity<PublicationDTO>(publicationService.updatePublication(publication, id), HttpStatus.OK);
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable("id") Integer id) {
		try {
			publicationService.deletePublication(id);
			return new ResponseEntity<PublicationDTO>(HttpStatus.OK);
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="/{bid}/request/{uid}")
	public ResponseEntity<?> requestBook(@PathVariable("bid") Integer bid, @PathVariable("uid") String uid) {
		try {
			if(ctService.requestBook(bid, uid)) {
				return new ResponseEntity<PublicationDTO>(HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Something was wrong"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="/{bid}/checkout/{uid}")
	public ResponseEntity<?> checkOutBook(@PathVariable("bid") Integer bid, @PathVariable("uid") String uid) {
		try {
			if(ctService.checkOutBook(bid, uid)) {
				return new ResponseEntity<PublicationDTO>(HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Something was wrong"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="/{bid}/checkin/{uid}")
	public ResponseEntity<?> checkInBook(@PathVariable("bid") Integer bid, @PathVariable("uid") String uid) {
		try {
			if(ctService.checkInBook(bid, uid)) {
				return new ResponseEntity<PublicationDTO>(HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Something was wrong"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/catalog")
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
	
	@GetMapping(path = "/catalog/{id}")
	public ResponseEntity<?> ctById(@PathVariable("id") Integer id) {
		try {
			GetCatalogEntryDTO ct =  ctService.getCatalogEntry(id);
			if (ct != null) {
				return new ResponseEntity<GetCatalogEntryDTO>(ct, HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiError>(new ApiError(HttpStatus.NOT_FOUND, "There were no catalog found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
