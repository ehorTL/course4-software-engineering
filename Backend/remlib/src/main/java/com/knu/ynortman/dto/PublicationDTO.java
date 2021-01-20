package com.knu.ynortman.dto;

import java.util.Date;

import com.knu.ynortman.entity.Publication;
import com.knu.ynortman.entity.Subject;
import com.knu.ynortman.entity.Type;

import lombok.Data;

@Data
public class PublicationDTO {
	private long id;
	private String title;
	private String identifier;
	private String edition;
	private String creator;
	private String publisher;
	private Subject subject;
	private String descrText;
	private byte[] descrPhoto;
	private String source;
	private Date publicationDate;
	private String contributor;
	private String rights;
	private String format;
	private String language;
	private Type type;
	
	public static PublicationDTO toDTO(Publication publication) {
		PublicationDTO publicationDTO = new PublicationDTO();
		publicationDTO.setId(publication.getId());
		publicationDTO.setTitle(publication.getTitle());
		publicationDTO.setIdentifier(publication.getIdentifier());
		publicationDTO.setEdition(publication.getEdition());
		publicationDTO.setCreator(publication.getCreator());
		publicationDTO.setPublisher(publication.getPublisher());
		publicationDTO.setSubject(publication.getSubject());
		publicationDTO.setDescrText(publication.getDescrText());
		publicationDTO.setDescrPhoto(publication.getDescrPhoto());
		publicationDTO.setSource(publication.getSource());
		publicationDTO.setPublicationDate(publication.getPublicationDate());
		publicationDTO.setContributor(publication.getContributor());
		publicationDTO.setRights(publication.getRights());
		publicationDTO.setFormat(publication.getFormat());
		publicationDTO.setLanguage(publication.getLanguage());
		publicationDTO.setType(publication.getType());
		return publicationDTO;
	}
	
	public static Publication fromDTO(PublicationDTO publicationDTO) {
		Publication publication = new Publication();
		publication.setId(publicationDTO.getId());
		publication.setTitle(publicationDTO.getTitle());
		publication.setIdentifier(publicationDTO.getIdentifier());
		publication.setEdition(publicationDTO.getEdition());
		publication.setCreator(publicationDTO.getCreator());
		publication.setPublisher(publicationDTO.getPublisher());
		publication.setSubject(publicationDTO.getSubject());
		publication.setDescrText(publicationDTO.getDescrText());
		publication.setDescrPhoto(publicationDTO.getDescrPhoto());
		publication.setSource(publicationDTO.getSource());
		publication.setPublicationDate(publicationDTO.getPublicationDate());
		publication.setContributor(publicationDTO.getContributor());
		publication.setRights(publicationDTO.getRights());
		publication.setFormat(publicationDTO.getFormat());
		publication.setLanguage(publicationDTO.getLanguage());
		publication.setType(publicationDTO.getType());
		return publication;
	}
}
