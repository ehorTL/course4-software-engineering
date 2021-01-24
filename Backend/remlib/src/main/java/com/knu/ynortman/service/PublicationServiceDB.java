package com.knu.ynortman.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.knu.ynortman.dto.PublicationDTO;
import com.knu.ynortman.entity.Publication;
import com.knu.ynortman.exception.ServerException;
import com.knu.ynortman.repository.PublicationRepository;
import com.knu.ynortman.repository.SubjectRepository;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

@Service
public class PublicationServiceDB implements PublicationService {

	private final PublicationRepository publicationRepo;
	private final SubjectRepository subjectRepo;
	
	public PublicationServiceDB(PublicationRepository publicationRepo, SubjectRepository subjectRepo) {
		this.publicationRepo = publicationRepo;
		this.subjectRepo = subjectRepo;
	}
	
	@Override
	public Iterable<PublicationDTO> getAllPublications() {
		List<PublicationDTO> publicationsDTO = new LinkedList<>();
		try {
			publicationRepo.findAll().forEach(new Consumer<Publication>() {
				@Override
				public void accept(Publication t) {
					publicationsDTO.add(PublicationDTO.toDTO(t));
				}
			});
					
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
		return publicationsDTO;
	}

	@Override
	public PublicationDTO getPublication(int id) {
		Optional<Publication> publication = null;
		try {
			publication = publicationRepo.findById((long)id);
			if(publication.isPresent()) {
				return PublicationDTO.toDTO(publication.get());
			}
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
		return null;
	}

	@Override
	public PublicationDTO addPublication(PublicationDTO publication) {
		try {
			if(subjectRepo.existsById(publication.getSubject().getId())) {
				Publication publ = PublicationDTO.fromDTO(publication);
				System.out.println(publ.getSubject());
				return PublicationDTO.toDTO(publicationRepo.save(publ));
			} else {
				throw new ServerException("Subject with id " + publication.getSubject().getId() + " does not exist");
			}
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}

	@Override
	public PublicationDTO updatePublication(PublicationDTO publication, int id) {
		try {
			Optional<Publication> oldPublication = publicationRepo.findById((long)id);
			if(oldPublication.isEmpty()) {
				return null;
			} else {
				publication.setId(id);
			}
			return PublicationDTO.toDTO(publicationRepo.save(PublicationDTO.fromDTO(publication)));
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}
	
	@Override
	public void deletePublication(long id) {
		try {
			publicationRepo.deleteById(id);
		} catch(Exception e) {
			throw new ServerException(e.getMessage());
		}

	}

}
