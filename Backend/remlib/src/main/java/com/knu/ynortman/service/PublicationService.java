package com.knu.ynortman.service;

import com.knu.ynortman.dto.PublicationDTO;

public interface PublicationService {
	Iterable<PublicationDTO> getAllPublications();
	PublicationDTO getPublication(int id);
	PublicationDTO addPublication(PublicationDTO publication);
	PublicationDTO updatePublication(PublicationDTO publication, int id);
}
