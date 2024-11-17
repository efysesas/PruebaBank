package com.bank.backPay.aplication.ports.in;

import com.bank.backPay.adapter.in.dto.Purchase;
import com.bank.backPay.adapter.out.entity.PurchaseEntity;

public interface PurchaseServiceIn {
	 /**
     * Realiza una compra usando los detalles proporcionados.
     * 
     * @param purchase el objeto Purchase con los datos de la compra
     * @return la entidad PurchaseEntity registrada en la base de datos
     */
    PurchaseEntity purchaseCard(Purchase purchase);

    /**
     * Obtiene los detalles de una compra por su ID de transacción.
     * 
     * @param transactionId el ID de la transacción
     * @return la entidad PurchaseEntity asociada con el ID de transacción
     */
    PurchaseEntity getPurchaseByTransactionId(String transactionId);

    /**
     * Anula una compra devolviendo el saldo a la tarjeta.
     * 
     * @param purchase el objeto Purchase con los datos necesarios para la anulación
     * @return la entidad PurchaseEntity actualizada con el estado de "anulado"
     */
    PurchaseEntity anulatePurchase(Purchase purchase);
}
