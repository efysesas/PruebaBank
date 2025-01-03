package com.bank.backPay.adapter.in.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Card {
	@NotNull(message = "El ID no puede ser nulo")
	@Size(min = 16, max = 16, message = "El cardId debe tener 16 caracteres")
	private String cardId;
	private double balance;
	private boolean active;
	@Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
	private String firstName;
	private String firstLastName;
	private Date expirationDate;
	private boolean blocked;
}
