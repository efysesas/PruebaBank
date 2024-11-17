package com.bank;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.backPay.adapter.out.entity.PurchaseEntity;
import com.bank.backPay.adapter.out.persistence.CardRepository;
import com.bank.backPay.adapter.out.persistence.PurchaseRepository;
import com.bank.backPay.aplication.services.purchases.PurchaseServiceImpl;
import com.bank.backPay.adapter.in.dto.Purchase;
import com.bank.backPay.adapter.out.entity.CardEntity;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    private CardEntity cardEntity;
    private PurchaseEntity purchaseEntity;

    @BeforeEach
    void setUp() {
        cardEntity = new CardEntity();
        cardEntity.setCardId("1234567890");
        cardEntity.setBalance(1000.0);
        cardEntity.setActive(true);
        cardEntity.setBlocked(false);

     // Asignar una fecha de expiración a la tarjeta
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusYears(3);  // La tarjeta expirará en un año
        cardEntity.setExpirationDate(Date.valueOf(localDate));
        
        purchaseEntity = new PurchaseEntity();
        purchaseEntity.setAppUid(UUID.randomUUID().toString());
        purchaseEntity.setCardId("1234567890");
        purchaseEntity.setPrice(100);
    }

    @Test
    void testPurchaseCard_Success() {
        when(cardRepository.findById("1234567890")).thenReturn(Optional.of(cardEntity));
        when(purchaseRepository.save(any(PurchaseEntity.class))).thenReturn(purchaseEntity);

        Purchase purchase = new Purchase();
        purchase.setCardId("1234567890");
        purchase.setPrice(100);

        PurchaseEntity result = purchaseService.purchaseCard(purchase);

        assertNotNull(result);
        assertEquals("1234567890", result.getCardId());
    }

    @Test
    void testGetPurchaseByTransactionId_Success() {
        when(purchaseRepository.findById(purchaseEntity.getAppUid())).thenReturn(Optional.of(purchaseEntity));

        PurchaseEntity result = purchaseService.getPurchaseByTransactionId(purchaseEntity.getAppUid());

        assertNotNull(result);
        assertEquals("1234567890", result.getCardId());
    }

    @Test
    void testAnulatePurchase_Success() {
        when(purchaseRepository.findById(purchaseEntity.getAppUid())).thenReturn(Optional.of(purchaseEntity));
        when(cardRepository.findById("1234567890")).thenReturn(Optional.of(cardEntity));

        Purchase purchase = new Purchase();
        purchase.setAppUid(purchaseEntity.getAppUid());

        PurchaseEntity result = purchaseService.anulatePurchase(purchase);

        assertNotNull(result);
        assertEquals("CANCELLED", result.getApprove());
    }
}
