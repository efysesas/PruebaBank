package com.bank.backPay.aplication.services.purchases;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.backPay.adapter.in.dto.Purchase;
import com.bank.backPay.adapter.out.entity.CardEntity;
import com.bank.backPay.adapter.out.entity.PurchaseEntity;
import com.bank.backPay.adapter.out.persistence.CardRepository;
import com.bank.backPay.adapter.out.persistence.PurchaseRepository;
import com.bank.backPay.aplication.ports.in.PurchaseServiceIn;

@Service
public class PurchaseServiceImpl implements PurchaseServiceIn{

	    @Autowired
	    private CardRepository cardRepository;

	    @Autowired
	    private PurchaseRepository purchaseRepository;

	    public PurchaseEntity purchaseCard(Purchase purchase) {

	        CardEntity existingCard = cardRepository.findById(purchase.getCardId())
	                .orElseThrow(() -> new RuntimeException("Card not found"));

	        if (!existingCard.isActive() || existingCard.isBlocked()) {
	            throw new RuntimeException("Card is not active or is blocked");
	        }

	        LocalDate localDate = LocalDate.now();
			Date currentDate = Date.valueOf(localDate);
	        if (existingCard.getExpirationDate().before(currentDate)) {
	            throw new RuntimeException("Card is expired");
	        }

	        if (existingCard.getBalance() < purchase.getPrice()) {
	            throw new RuntimeException("Insufficient balance");
	        }

	        String transactionId = UUID.randomUUID().toString();

	        PurchaseEntity newPurchase = new PurchaseEntity();
	        newPurchase.setAppUid(transactionId);
	        newPurchase.setCardId(purchase.getCardId());
	        newPurchase.setPrice(purchase.getPrice());
	        newPurchase.setDatePay(currentDate);
	        newPurchase.setApprove("PENDING");

	        purchaseRepository.save(newPurchase);

	        existingCard.setBalance(existingCard.getBalance() - purchase.getPrice());
	        cardRepository.save(existingCard);

	        return newPurchase;
	    }

	    public PurchaseEntity getPurchaseByTransactionId(String transactionId) {
	        return purchaseRepository.findById(transactionId)
	                .orElseThrow(() -> new RuntimeException("Transaction not found"));
	    }

	    public PurchaseEntity anulatePurchase(Purchase purchase) {
	        PurchaseEntity existingPurchase = purchaseRepository.findById(purchase.getAppUid())
	                .orElseThrow(() -> new RuntimeException("Purchase not found"));

	        CardEntity card = cardRepository.findById(existingPurchase.getCardId())
	                .orElseThrow(() -> new RuntimeException("Card not found"));

	        card.setBalance(card.getBalance() + existingPurchase.getPrice());
	        cardRepository.save(card);

	        existingPurchase.setApprove("CANCELLED");
	        return purchaseRepository.save(existingPurchase);
	    }
	}
