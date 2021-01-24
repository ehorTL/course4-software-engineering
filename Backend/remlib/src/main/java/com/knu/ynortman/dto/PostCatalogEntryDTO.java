package com.knu.ynortman.dto;


import com.knu.ynortman.entity.CatalogEntry;
import com.knu.ynortman.entity.Library;
import com.knu.ynortman.entity.Publication;

import lombok.Data;

@Data
public class PostCatalogEntryDTO {
	private long id;
	private String itemNumber;
	private Publication publication;
	private Library library;
	private int copiesNumber;
	private int loanDays;
	
	public static PostCatalogEntryDTO toDTO(CatalogEntry ct) {
		PostCatalogEntryDTO ctDTO = new PostCatalogEntryDTO();
		ctDTO.setId(ct.getId());
		ctDTO.setItemNumber(ct.getItemNumber());
		ctDTO.setPublication(ct.getPublication());
		ctDTO.setLibrary(ct.getLibrary());
		ctDTO.setCopiesNumber(ct.getCopiesNumber());
		ctDTO.setLoanDays(ct.getLoanDays());
		return ctDTO;
	}
	
	public static CatalogEntry fromDTO(PostCatalogEntryDTO ctDTO) {
		CatalogEntry ct = new CatalogEntry();
		ct.setId(ctDTO.getId());
		ct.setItemNumber(ctDTO.getItemNumber());
		ct.setPublication(ctDTO.getPublication());
		ct.setLibrary(ctDTO.getLibrary());
		ct.setCopiesNumber(ctDTO.getCopiesNumber());
		ct.setLoanDays(ctDTO.getLoanDays());
		return ct;
	}
}
