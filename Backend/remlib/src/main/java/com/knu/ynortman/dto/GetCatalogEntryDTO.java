package com.knu.ynortman.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.knu.ynortman.entity.CatalogEntry;
import com.knu.ynortman.entity.Library;
import com.knu.ynortman.entity.Publication;
import com.knu.ynortman.entity.Status;

import lombok.Data;

@Data
public class GetCatalogEntryDTO {
	private long id;
	@NotNull(message = "item number cannot be null")
	private String itemNumber;
	private Publication publication;
	private Library library;
	private Status status;
	private Date avlblFrom;
	@Min(0)
	private int copiesNumber;
	@Min(0)
	private int copiesAvlbl;
	private int loanDays;
	
	public static GetCatalogEntryDTO toDTO(CatalogEntry ct) {
		GetCatalogEntryDTO ctDTO = new GetCatalogEntryDTO();
		ctDTO.setId(ct.getId());
		ctDTO.setItemNumber(ct.getItemNumber());
		ctDTO.setPublication(ct.getPublication());
		ctDTO.setLibrary(ct.getLibrary());
		ctDTO.setStatus(ct.getStatus());
		ctDTO.setAvlblFrom(ct.getAvlblFrom());
		ctDTO.setCopiesNumber(ct.getCopiesNumber());
		ctDTO.setCopiesAvlbl(ct.getCopiesAvlbl());
		ctDTO.setLoanDays(ct.getLoanDays());
		return ctDTO;
	}
	
	public static CatalogEntry fromDTO(GetCatalogEntryDTO ctDTO) {
		CatalogEntry ct = new CatalogEntry();
		ct.setId(ctDTO.getId());
		ct.setItemNumber(ctDTO.getItemNumber());
		ct.setPublication(ctDTO.getPublication());
		ct.setLibrary(ctDTO.getLibrary());
		ct.setStatus(ctDTO.getStatus());
		ct.setAvlblFrom(ctDTO.getAvlblFrom());
		ct.setCopiesNumber(ctDTO.getCopiesNumber());
		ct.setCopiesAvlbl(ctDTO.getCopiesAvlbl());
		ct.setLoanDays(ctDTO.getLoanDays());
		return ct;
	}
}
