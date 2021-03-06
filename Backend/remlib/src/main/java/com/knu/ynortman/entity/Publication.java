package com.knu.ynortman.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@Entity
public class Publication {
	@Id
	@GeneratedValue(generator = "publication_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "publication_id_seq", sequenceName = "publication_id_seq", allocationSize = 2)
	private long id;
	private String title;
	private String identifier;
	private String edition;
	private String creator;
	private String publisher;
	@ManyToOne
	private Subject subject;
	private String descrText;
	@Lob
	@org.hibernate.annotations.Type(type="org.hibernate.type.BinaryType")
	private byte[] descrPhoto;
	private String source;
	private Date publicationDate;
	private String contributor;
	private String rights;
	private String format;
	private String language;
	@ManyToOne
	private Type type;
}
