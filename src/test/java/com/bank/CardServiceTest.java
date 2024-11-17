package com.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.backPay.adapter.in.dto.Card;
import com.bank.backPay.adapter.out.entity.CardEntity;
import com.bank.backPay.adapter.out.persistence.CardRepository;
import com.bank.backPay.aplication.services.cards.CardServiceImpl;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

	@InjectMocks
    private CardServiceImpl cardService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private ModelMapper modelMapper;

    @SuppressWarnings("unchecked")
	@Test
    void generateCardNumber() {
        // Arrange
        String productId = "123";
        String randomNumbers = "1234567890"; // Simula el número aleatorio
        String cardNumber = productId + randomNumbers;

        Card card = new Card();
        card.setCardId(cardNumber);
        card.setBalance(0.0);
        card.setActive(false);
        CardEntity cardEntity = new CardEntity();

        // Usa doReturn() para evitar problemas de coincidencia de argumentos
        doReturn(cardEntity).when(modelMapper).map(any(Card.class), any(Class.class));
        when(cardRepository.save(cardEntity)).thenReturn(cardEntity);

        // Act
        ResponseEntity<Object> response = cardService.generateCardNumber(productId);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Card number generated: " + cardNumber, response.getBody());
    }

    @Test
    void activateCard() {
        // Arrange
        Card card = new Card();
        card.setCardId("1234567890");
        CardEntity existingCard = new CardEntity();
        existingCard.setBlocked(false);
        when(cardRepository.findById(card.getCardId())).thenReturn(Optional.of(existingCard));

        // Act
        ResponseEntity<Object> response = cardService.activateCard(card);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Card with ID 1234567890 activated successfully", response.getBody());
    }

    @Test
    void blockCard() {
        // Arrange
        String cardId = "1234567890";
        CardEntity existingCard = new CardEntity();
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(existingCard));

        // Act
        ResponseEntity<Object> response = cardService.blockCard(cardId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Card with ID 1234567890 has been blocked", response.getBody());
    }

    @Test
    void updateBalance() {
        // Arrange
        Card card = new Card();
        card.setCardId("1234567890");
        card.setBalance(10.0);
        CardEntity existingCard = new CardEntity();
        existingCard.setBalance(5.0);
        when(cardRepository.findById(card.getCardId())).thenReturn(Optional.of(existingCard));

        // Act
        ResponseEntity<Object> response = cardService.updateBalance(card);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Balance updated successfully. New balance: 15.0", response.getBody());
    }

    @Test
    void getCardBalance() {
        // Arrange
        String cardId = "1234567890";
        CardEntity card = new CardEntity();
        card.setBalance(10.0);
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        // Act
        ResponseEntity<Object> response = cardService.getCardBalance(cardId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Balance of card with ID 1234567890: 10.0", response.getBody());
    }
}
