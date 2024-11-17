package com.bank.backPay.adapter.out.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CardEntity {
	@Id
	private String cardId;
	private double balance;
	private boolean active;
	private String firstName;
	private String firstLastName;
	private Date expirationDate;
	private boolean blocked;
}
