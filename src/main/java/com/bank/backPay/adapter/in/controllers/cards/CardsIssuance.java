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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("/card")
public class CardsIssuance {
	
	@Autowired
	CardServiceIn cardServiceIn;
	
	@GetMapping("/{productId}/number")
	@Operation(summary = "obtener numero de tarjeta", description = "obtener numero de tarjeta")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud inválida", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
					@Content(mediaType = "application/json") }) })
	public ResponseEntity<Object> numberCardGenerate(@PathVariable String productId){
		return cardServiceIn.generateCardNumber(productId);
	}
	
	@PostMapping("/enroll")
	@Operation(summary = "asociar y activar tarjeta", description = "asociar y activar tarjeta")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud inválida", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
					@Content(mediaType = "application/json") }) })
	public ResponseEntity<Object> activeCard(@Valid @RequestBody Card card){
		return cardServiceIn.activateCard(card);
	}
	
	@DeleteMapping("/{cardId}")
	@Operation(summary = "Bloquear tarjeta", description = "Bloquear tarjeta")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud inválida", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
					@Content(mediaType = "application/json") }) })
	public ResponseEntity<Object> blockCard(@PathVariable String cardId){
		return cardServiceIn.blockCard(cardId);
	}
	
	@PostMapping("/balance")
	@Operation(summary = "Recargar tarjeta", description = "Recargar tarjeta")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud inválida", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
					@Content(mediaType = "application/json") }) })
	public ResponseEntity<Object> updateBalance(@Valid @RequestBody Card card){
		return cardServiceIn.updateBalance(card);
	}
	
	@GetMapping("/balance/{cardId}")
	@Operation(summary = "consultar balance de tarjeta", description = "consultar balance de tarjeta")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud inválida", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
					@Content(mediaType = "application/json") }) })
	public ResponseEntity<Object> getCardBalance(@PathVariable String cardId){
		return cardServiceIn.getCardBalance(cardId);
	}
	
}
