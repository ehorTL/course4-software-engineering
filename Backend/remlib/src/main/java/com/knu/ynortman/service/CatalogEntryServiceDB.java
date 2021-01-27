package com.knu.ynortman.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.knu.ynortman.constants.LoanStatusConst;
import com.knu.ynortman.dto.GetCatalogEntryDTO;
import com.knu.ynortman.dto.PostCatalogEntryDTO;
import com.knu.ynortman.entity.CatalogEntry;
import com.knu.ynortman.entity.LoanCard;
import com.knu.ynortman.entity.LoanStatus;
import com.knu.ynortman.entity.Status;
import com.knu.ynortman.entity.User;
import com.knu.ynortman.enums.LoanStatusEnum;
import com.knu.ynortman.enums.StatusEnum;
import com.knu.ynortman.exception.ServerException;
import com.knu.ynortman.repository.CatalogEntryRepository;
import com.knu.ynortman.repository.LoanCardRepository;
import com.knu.ynortman.repository.LoanStatusRepository;
import com.knu.ynortman.repository.StatusRepository;
import com.knu.ynortman.repository.UserRepository;
import com.knu.ynortman.util.DateUtil;
import com.knu.ynortman.util.EmailUtil;

import static com.knu.ynortman.util.DateUtil.*;


@Service
public class CatalogEntryServiceDB implements CatalogEntryService {

	private final CatalogEntryRepository ctRepo;
	private final StatusRepository statusRepo;
	private final LoanCardRepository loanRepo;
	private final UserRepository userRepo;
	private final LoanStatusRepository loanStatusRepo;
	private final EmailUtil email;
	
	public final int REQUESTED = 0;
	public final int CHECKED_OUT = 1;
	public final int CHECKED_IN = 2;
	
	public final int AVAILABLE = 0;
	public final int NON_CIRCULATING = 2;
	public final int QUEUE = 3;
	
	public CatalogEntryServiceDB(CatalogEntryRepository ctRepo, StatusRepository statusRepo, LoanCardRepository loanRepo, 
			UserRepository userRepo, LoanStatusRepository loanStatusRepo, EmailUtil email) {
		this.ctRepo = ctRepo;
		this.statusRepo = statusRepo;
		this.loanRepo = loanRepo;
		this.userRepo = userRepo;
		this.loanStatusRepo = loanStatusRepo;
		this.email = email;
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
				if(oldCatalog.getCopiesAvlbl() > oldCatalog.getCopiesNumber()) {
					oldCatalog.setCopiesAvlbl(oldCatalog.getCopiesNumber());
				}
				oldCatalog.setLoanDays(newCatalog.getLoanDays());
				oldCatalog.setStatus(newCatalog.getStatus());
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
	
	@Override
	public boolean requestBook(long catalogId, String uid) throws Exception {
		CatalogEntry ct = ctRepo.findById(catalogId).orElseThrow(() -> new Exception("Catalog entry with id " + catalogId + " does not exist"));
		User user = userRepo.findById(uid).orElseThrow(() -> new Exception("User with id " + uid + " does not exist"));
		if(loanRepo.findByCatalogEntryAndUserAndLoanStatus(ct, user, loanStatusRepo.findById(REQUESTED).get()).isPresent() ||
				loanRepo.findByCatalogEntryAndUserAndLoanStatus(ct, user, loanStatusRepo.findById(CHECKED_OUT).get()).isPresent()) {
			throw new Exception("User has already requested this book");
		}
		if(ct.getStatus().equals(statusRepo.findById(NON_CIRCULATING).get())) {
			throw new Exception("This publication is non-circulating");
		} else if(ct.getStatus().equals(statusRepo.findById(AVAILABLE).get())) { //book is available
			ct.setCopiesAvlbl(ct.getCopiesAvlbl()-1);
			if(ct.getCopiesAvlbl() == 0) {
				ct.setStatus(statusRepo.findById(QUEUE).get());
				List<LoanCard> loancards = (List<LoanCard>) loanRepo.findByCatalogEntryAndLoanStatus(ct, loanStatusRepo.findById(REQUESTED).get());
				loancards.addAll((List<LoanCard>) loanRepo.findByCatalogEntryAndLoanStatus(ct, loanStatusRepo.findById(CHECKED_OUT).get()));
				Collections.sort(loancards, new Comparator<LoanCard>() {
					public int compare(LoanCard l1, LoanCard l2) {
						return l2.getLoanUntil().compareTo(l1.getLoanUntil());
					}
				});
				ct.setAvlblFrom(loancards.get(0).getLoanUntil());
				ctRepo.save(ct);
			}
			LoanCard card = new LoanCard();
			card.setCatalogEntry(ct);
			card.setUser(user);
			card.setAvlblFrom(new Date());
			card.setLoanUntil(DateUtil.addDays(card.getAvlblFrom(), ct.getLoanDays()));
			card.setLoanStatus(loanStatusRepo.findById(REQUESTED).get());
			card.notification();
			email.sendSimpleMessage(user.getEmail(), "Book notification", messageText(card));
			loanRepo.save(card);
			 
		} else { // all books are requested
			List<LoanCard> loancards = (List<LoanCard>) loanRepo.findByCatalogEntryAndLoanStatus(ct, loanStatusRepo.findById(CHECKED_OUT).get());
			loancards.addAll((List<LoanCard>) loanRepo.findByCatalogEntryAndLoanStatus(ct, loanStatusRepo.findById(REQUESTED).get()));
			Collections.sort(loancards, new Comparator<LoanCard>() {
				public int compare(LoanCard l1, LoanCard l2) {
					return l1.getLoanUntil().compareTo(l2.getLoanUntil());
				}
			});
			int bookNumber = ct.getCopiesNumber();
			int loanCardsNumber = loancards.size();
			ct.setAvlblFrom(loancards.get(loanCardsNumber-bookNumber+1).getLoanUntil());
			ctRepo.save(ct);
			LoanCard card = new LoanCard();
			card.setCatalogEntry(ct);
			card.setUser(user);
			card.setAvlblFrom(loancards.get(loanCardsNumber-bookNumber).getLoanUntil());
			card.setLoanUntil(DateUtil.addDays(card.getAvlblFrom(), ct.getLoanDays()));
			card.setLoanStatus(loanStatusRepo.findById(REQUESTED).get());
			loanRepo.save(card);
		}
		return true;
	}
	
	@Override
	public boolean checkOutBook(long catalogId, String uid) throws Exception {
		CatalogEntry ct = ctRepo.findById(catalogId).orElseThrow(() -> new Exception("Catalog entry with id " + catalogId + " does not exist"));
		User user = userRepo.findById(uid).orElseThrow(() -> new Exception("User with id " + uid + " does not exist"));
		System.out.println("Before requested");
		LoanStatus ls = loanStatusRepo.findById(0).get();
		//System.out.println(ls);
		//System.out.println(LoanStatusEnum.REQUESTED.getStatus());
		LoanCard loanCard = loanRepo.findByCatalogEntryAndUserAndLoanStatus(ct, user, loanStatusRepo.findById(REQUESTED).get()).
					orElseThrow(() -> new Exception(
							"User with id " + uid + " has not requested publication with id " + ct.getPublication().getIdentifier()));
		System.out.println("After requested");
		if(loanCard.getLoanUntil().compareTo(new Date()) < 0) {
			throw new Exception("Too late to check out this book");
		}
		if(loanCard.getAvlblFrom().compareTo(new Date()) > 0) {
			throw new Exception("Too early to check out this book");
		}
		if(!loanCard.getLoanStatus().equals(loanStatusRepo.findById(REQUESTED).get())) {
			throw new Exception("Invalid loan status! Must be requested!");
		}
		if(!loanCard.isNotified()) {
			throw new Exception("You have not been notified yet");
		}
		loanCard.setCheckedOut(new Date());
		loanCard.setLoanStatus(loanStatusRepo.findById(CHECKED_OUT).get());
		loanRepo.save(loanCard);
		return true;
	}
	
	@Override
	public boolean checkInBook(long catalogId, String uid) {
		final int fee = 100;
		CatalogEntry ct = ctRepo.findById(catalogId).get();
		User user = userRepo.findById(uid).get();
		LoanCard loanCard = loanRepo.findByCatalogEntryAndUserAndLoanStatus(ct, user, loanStatusRepo.findById(CHECKED_OUT).get()).get();
		loanCard.setCheckedOut(new Date());
		loanCard.setLoanStatus(loanStatusRepo.findById(CHECKED_IN).get());
		if(loanCard.getCheckedOut().compareTo(loanCard.getLoanUntil()) > 0) {
			System.out.println("fee");
			user.setFee(user.getFee() + fee);
			userRepo.save(user);
		}
		
		loanRepo.save(loanCard);
		
		List<LoanCard> loancards = (List<LoanCard>) loanRepo.findByCatalogEntryAndLoanStatusAndNotified(
				ct, loanStatusRepo.findById(REQUESTED).get(), false);
		Collections.sort(loancards, new Comparator<LoanCard>() {
			public int compare(LoanCard l1, LoanCard l2) {
				return l1.getLoanUntil().compareTo(l2.getLoanUntil());
			}
		});
		if(loancards.size() == 0) {
			ct.setStatus(statusRepo.findById(AVAILABLE).get());
			ct.setCopiesAvlbl(ct.getCopiesAvlbl()+1);
			ct.setAvlblFrom(new Date());
			ctRepo.save(ct);
		} else {
			LoanCard nextLoan = loancards.get(0);
			nextLoan.notification();
			nextLoan.setAvlblFrom(new Date());
			nextLoan.setLoanUntil(DateUtil.addDays(nextLoan.getAvlblFrom(), ct.getLoanDays()));
			email.sendSimpleMessage(user.getEmail(), "Book notification", messageText(nextLoan));
			loanRepo.save(nextLoan);
		}
		return true;
	}
	
	
	private String messageText(LoanCard loancard) {
		return "Requested book " + loancard.getCatalogEntry().getPublication().getTitle() + " by " 
				+ loancard.getCatalogEntry().getPublication().getCreator() + ", ISBN: "
				+ loancard.getCatalogEntry().getPublication().getIdentifier() + " is now available. You can pick it up in the library "
				+ loancard.getCatalogEntry().getLibrary().getName() + ", address: " + loancard.getCatalogEntry().getLibrary().getAddress()
				+ ". The book is availiable until " + loancard.getLoanUntil() + ".";
				
	}
}
