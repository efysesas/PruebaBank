package com.bank.backPay.adapter.in.controllers.purchases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.backPay.adapter.in.dto.Purchase;
import com.bank.backPay.adapter.out.entity.PurchaseEntity;
import com.bank.backPay.aplication.ports.in.PurchaseServiceIn;

@RestController
@RequestMapping("/transaction")
public class PurchasesCards {
	

    @Autowired
    private PurchaseServiceIn purchaseServiceIn;

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseCard(@RequestBody Purchase purchase) {
        try {
            PurchaseEntity result = purchaseServiceIn.purchaseCard(purchase);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Object> getPurchaseCard(@PathVariable String transactionId) {
        try {
            PurchaseEntity result = purchaseServiceIn.getPurchaseByTransactionId(transactionId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/anulation")
    public ResponseEntity<Object> anulatePurchase(@RequestBody Purchase purchase) {
        try {
            PurchaseEntity result = purchaseServiceIn.anulatePurchase(purchase);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
	
}
