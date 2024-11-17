package com.bank.backPay.aplication.services.cards;

import java.sql.Date;
import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bank.backPay.adapter.in.dto.Card;
import com.bank.backPay.adapter.out.entity.CardEntity;
import com.bank.backPay.adapter.out.persistence.CardRepository;
import com.bank.backPay.aplication.ports.in.CardServiceIn;

@Service
public class CardServiceImpl implements CardServiceIn {

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<Object> generateCardNumber(String productId) {
		String randomNumbers = String.format("%010d", (int) (Math.random() * 10000000000L));
		String cardNumber = productId + randomNumbers;

		Card card = new Card();
		card.setCardId(cardNumber);
		card.setBalance(0.0);
		card.setActive(false);

		CardEntity cardRegister = modelMapper.map(card, CardEntity.class);

		cardRepository.save(cardRegister);

		return ResponseEntity.status(HttpStatus.CREATED).body("Card number generated: " + cardNumber);
	}

	public ResponseEntity<Object> activateCard(Card card) {
		
		CardEntity existingCard = cardRepository.findById(card.getCardId())
			    .orElseThrow(() -> new RuntimeException("Card not found"));
	   
		LocalDate localDate = LocalDate.now();
		localDate = localDate.plusYears(3);
		Date dateExpiration = Date.valueOf(localDate);
		
		try {
			
			if(existingCard.isBlocked()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card with ID" + card.getCardId() + "BLOCKED");
			}
			
			card.setActive(true);
			card.setExpirationDate(dateExpiration);
			card.setBlocked(false);
			
			CardEntity cardRegister = modelMapper.map(card, CardEntity.class);

			cardRepository.save(cardRegister);
			
			return ResponseEntity.status(HttpStatus.OK)
					.body("Card with ID " + card.getCardId() + " activated successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card with ID " + card.getCardId() + " not found");
		}
	}

	public ResponseEntity<Object> blockCard(String cardId) {
		CardEntity existingCard = cardRepository.findById(cardId)
			    .orElseThrow(() -> new RuntimeException("Card not found"));
		try {
			
			existingCard.setBlocked(true);
			
			cardRepository.save(existingCard);
			return ResponseEntity.status(HttpStatus.OK).body("Card with ID " + cardId + " has been blocked");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card with ID " + cardId + " not found");
		}
	}

	public ResponseEntity<Object> updateBalance(Card card) {
		try {
			CardEntity existingCard = cardRepository.findById(card.getCardId())
					.orElseThrow(() -> new RuntimeException("Card not found"));

			double newBalance = existingCard.getBalance() + card.getBalance();

			if (newBalance < 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Insufficient balance: cannot set balance below $0");
			}

			existingCard.setBalance(newBalance);
			
			cardRepository.save(existingCard);

			return ResponseEntity.status(HttpStatus.OK)
					.body("Balance updated successfully. New balance: " + newBalance);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card with ID " + card.getCardId() + " not found");
		}
	}

	public ResponseEntity<Object> getCardBalance(String cardId) {
		try {
			CardEntity card = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));

			return ResponseEntity.status(HttpStatus.OK)
					.body("Balance of card with ID " + cardId + ": " + card.getBalance());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card with ID " + cardId + " not found");
		}
	}
}
