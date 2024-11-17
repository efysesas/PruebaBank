package com.bank.backPay.aplication.ports.in;

import org.springframework.http.ResponseEntity;

import com.bank.backPay.adapter.in.dto.Card;

public interface CardServiceIn {
	/**
     * Genera un número de tarjeta único basado en un ID de producto específico.
     * 
     * @param productId El ID del producto para el cual se generará el número de tarjeta.
     * @return Un ResponseEntity que contiene el número de tarjeta generado o un mensaje de error si no se puede generar.
     */
    ResponseEntity<Object> generateCardNumber(String productId);

    /**
     * Activa una tarjeta para que pueda ser utilizada en transacciones.
     * 
     * @param card Un objeto Card que contiene los detalles de la tarjeta a activar.
     * @return Un ResponseEntity que indica si la tarjeta fue activada correctamente o un mensaje de error si falló la activación.
     */
    ResponseEntity<Object> activateCard(Card card);

    /**
     * Bloquea una tarjeta para evitar su uso en transacciones (por ejemplo, en caso de pérdida o robo).
     * 
     * @param cardId El ID de la tarjeta que se desea bloquear.
     * @return Un ResponseEntity que confirma el bloqueo de la tarjeta o un mensaje de error si no se pudo bloquear.
     */
    ResponseEntity<Object> blockCard(String cardId);

    /**
     * Actualiza el balance de una tarjeta con un nuevo valor (esto puede ser para agregar o reducir el saldo).
     * 
     * @param card Un objeto Card que contiene el ID de la tarjeta y el nuevo balance a establecer.
     * @return Un ResponseEntity que confirma la actualización del balance o un mensaje de error si falló la operación.
     */
    ResponseEntity<Object> updateBalance(Card card);

    /**
     * Obtiene el balance actual de una tarjeta específica.
     * 
     * @param cardId El ID de la tarjeta de la cual se desea consultar el balance.
     * @return Un ResponseEntity que contiene el balance de la tarjeta o un mensaje de error si la tarjeta no existe.
     */
    ResponseEntity<Object> getCardBalance(String cardId);
}
