package com.knu.ynortman.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knu.ynortman.dto.PublicationDTO;
import com.knu.ynortman.exception.ApiError;
import com.knu.ynortman.exception.ServerException;
import com.knu.ynortman.service.PublicationService;

@RestController
@RequestMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class BookController {

	private final PublicationService publicationService;

	public BookController(PublicationService publicationService) {
		this.publicationService = publicationService;
	}

	@GetMapping
	public ResponseEntity<?> bookList() {
		try {
			List<PublicationDTO> publications = (List<PublicationDTO>) publicationService.getAllPublications();
			if (publications == null || publications.size() == 0) {
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
	public ResponseEntity<?> postBook(@RequestBody PublicationDTO publication) {
		try {
			return new ResponseEntity<PublicationDTO>(publicationService.addPublication(publication), HttpStatus.CREATED);
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putAlgorithm(@RequestBody PublicationDTO publication, @PathVariable int id) {
		try {
			return new ResponseEntity<PublicationDTO>(publicationService.updatePublication(publication, id), HttpStatus.OK);
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
