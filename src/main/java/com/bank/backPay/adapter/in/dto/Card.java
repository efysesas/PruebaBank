package com.bank.backPay.adapter.in.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Card {
	private String cardId;
	private double balance;
	private boolean active;
	private String firstName;
	private String firstLastName;
	private Date expirationDate;
	private boolean blocked;
}
