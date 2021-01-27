package com.knu.ynortman.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.knu.ynortman.dto.GetCatalogEntryDTO;
import com.knu.ynortman.dto.PostCatalogEntryDTO;
import com.knu.ynortman.entity.CatalogEntry;
import com.knu.ynortman.entity.LoanCard;
import com.knu.ynortman.entity.User;
import com.knu.ynortman.exception.ServerException;
import com.knu.ynortman.repository.CatalogEntryRepository;
import com.knu.ynortman.repository.LoanCardRepository;
import com.knu.ynortman.repository.LoanStatusRepository;
import com.knu.ynortman.repository.StatusRepository;
import com.knu.ynortman.repository.UserRepository;
import com.knu.ynortman.util.DateUtil;

import static com.knu.ynortman.util.DateUtil.*;


@Service
public class CatalogEntryServiceDB implements CatalogEntryService {

	private final CatalogEntryRepository ctRepo;
	private final StatusRepository statusRepo;
	private final LoanCardRepository loanRepo;
	private final UserRepository userRepo;
	private final LoanStatusRepository loanStatusRepo;
	
	public CatalogEntryServiceDB(CatalogEntryRepository ctRepo, StatusRepository statusRepo, LoanCardRepository loanRepo, 
			UserRepository userRepo, LoanStatusRepository loanStatusRepo) {
		this.ctRepo = ctRepo;
		this.statusRepo = statusRepo;
		this.loanRepo = loanRepo;
		this.userRepo = userRepo;
		this.loanStatusRepo = loanStatusRepo;
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
		CatalogEntry ct = ctRepo.findById(catalogId).get();
		User user = userRepo.findById(uid).get();
		if(loanRepo.findByCatalogEntryAndUserAndLoanStatus(ct, user, loanStatusRepo.findById(0).get()).isPresent() ||
				loanRepo.findByCatalogEntryAndUserAndLoanStatus(ct, user, loanStatusRepo.findById(1).get()).isPresent()) {
			System.out.println("User has already requested this book");
			return false;
		}
		if(ct.getStatus().getId() == 2) {
			System.out.println("Non circulating");
			throw new Exception("This publication is non-circulating");
		} else if(ct.getStatus().getId() == 0) { //book is available
			ct.setCopiesAvlbl(ct.getCopiesAvlbl()-1);
			if(ct.getCopiesAvlbl() == 0) {
				ct.setStatus(statusRepo.findById(3).get());
				List<LoanCard> loancards = (List<LoanCard>) loanRepo.findByCatalogEntryAndLoanStatus(ct, loanStatusRepo.findById(1).get());
				loancards.addAll((List<LoanCard>) loanRepo.findByCatalogEntryAndLoanStatus(ct, loanStatusRepo.findById(0).get()));
				Collections.sort(loancards, new Comparator<LoanCard>() {
					public int compare(LoanCard l1, LoanCard l2) {
						return l2.getLoanUntil().compareTo(l1.getLoanUntil());
					}
				});
				System.out.println(loancards.get(0).getLoanUntil());
				ct.setAvlblFrom(loancards.get(0).getLoanUntil());
				ctRepo.save(ct);
			}
			LoanCard card = new LoanCard();
			card.setCatalogEntry(ct);
			card.setUser(user);
			card.setAvlblFrom(new Date());
			card.setLoanUntil(DateUtil.addDays(card.getAvlblFrom(), ct.getLoanDays()));
			card.setLoanStatus(loanStatusRepo.findById(0).get());
			card.notification();
			loanRepo.save(card);
			 
		} else { // all books are requested
			List<LoanCard> loancards = (List<LoanCard>) loanRepo.findByCatalogEntryAndLoanStatus(ct, loanStatusRepo.findById(1).get());
			loancards.addAll((List<LoanCard>) loanRepo.findByCatalogEntryAndLoanStatus(ct, loanStatusRepo.findById(0).get()));
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
			card.setLoanStatus(loanStatusRepo.findById(0).get());
			loanRepo.save(card);
		}
		return true;
	}
	
	@Override
	public boolean checkOutBook(long catalogId, String uid) {
		CatalogEntry ct = ctRepo.findById(catalogId).get();
		User user = userRepo.findById(uid).get();
		LoanCard loanCard = loanRepo.findByCatalogEntryAndUserAndLoanStatus(ct, user, loanStatusRepo.findById(0).get()).get();
		if(loanCard.getLoanUntil().compareTo(new Date()) < 0) {
			System.out.println("Too late to check out this book");
			return false;
		}
		if(loanCard.getAvlblFrom().compareTo(new Date()) > 0) {
			System.out.println("Too early to check out this book");
			return false;
		}
		if(loanCard.getLoanStatus().getId() != 0) {
			System.out.println("Invalid loan status");
			return false;
		}
		if(!loanCard.isNotified()) {
			System.out.println("You have not been notified yet");
			return false;
		}
		loanCard.setCheckedOut(new Date());
		loanCard.setLoanStatus(loanStatusRepo.findById(1).get());
		loanRepo.save(loanCard);
		return true;
	}
	
	@Override
	public boolean checkInBook(long catalogId, String uid) {
		final int fee = 100;
		CatalogEntry ct = ctRepo.findById(catalogId).get();
		User user = userRepo.findById(uid).get();
		LoanCard loanCard = loanRepo.findByCatalogEntryAndUserAndLoanStatus(ct, user, loanStatusRepo.findById(1).get()).get();
		loanCard.setCheckedOut(new Date());
		loanCard.setLoanStatus(loanStatusRepo.findById(2).get());
		if(loanCard.getCheckedOut().compareTo(loanCard.getLoanUntil()) > 0) {
			System.out.println("fee");
			user.setFee(user.getFee() + fee);
			userRepo.save(user);
		}
		
		loanRepo.save(loanCard);
		
		List<LoanCard> loancards = (List<LoanCard>) loanRepo.findByCatalogEntryAndLoanStatusAndNotified(ct, loanStatusRepo.findById(0).get(), false);
		Collections.sort(loancards, new Comparator<LoanCard>() {
			public int compare(LoanCard l1, LoanCard l2) {
				return l1.getLoanUntil().compareTo(l2.getLoanUntil());
			}
		});
		if(loancards.size() == 0) {
			ct.setStatus(statusRepo.findById(0).get());
			ct.setCopiesAvlbl(ct.getCopiesAvlbl()+1);
			ct.setAvlblFrom(new Date());
			ctRepo.save(ct);
		} else {
			LoanCard nextLoan = loancards.get(0);
			nextLoan.notification();
			nextLoan.setAvlblFrom(new Date());
			nextLoan.setLoanUntil(DateUtil.addDays(nextLoan.getAvlblFrom(), ct.getLoanDays()));
			loanRepo.save(nextLoan);
		}
		return true;
	}

}
