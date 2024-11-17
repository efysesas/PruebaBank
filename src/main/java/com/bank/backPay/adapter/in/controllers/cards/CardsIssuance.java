package com.bank.backPay.adapter.in.controllers.cards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.backPay.adapter.in.dto.Card;
import com.bank.backPay.aplication.ports.in.CardServiceIn;

@RestController
@RequestMapping("/card")
public class CardsIssuance {
	
	@Autowired
	CardServiceIn cardServiceIn;
	
	@GetMapping("/{productId}/number")
	public ResponseEntity<Object> numberCardGenerate(@PathVariable String productId){
		return cardServiceIn.generateCardNumber(productId);
	}
	
	@PostMapping("/enroll")
	public ResponseEntity<Object> activeCard(@RequestBody Card card){
		return cardServiceIn.activateCard(card);
	}
	
	@DeleteMapping("/{cardId}")
	public ResponseEntity<Object> blockCard(@PathVariable String cardId){
		return cardServiceIn.blockCard(cardId);
	}
	
	@PostMapping("/balance")
	public ResponseEntity<Object> updateBalance(@RequestBody Card card){
		return cardServiceIn.updateBalance(card);
	}
	
	@GetMapping("/balance/{cardId}")
	public ResponseEntity<Object> getCardBalance(@PathVariable String cardId){
		return cardServiceIn.getCardBalance(cardId);
	}
	
}
