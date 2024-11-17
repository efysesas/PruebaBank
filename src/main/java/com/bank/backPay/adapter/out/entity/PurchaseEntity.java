package com.bank.backPay.adapter.out.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PurchaseEntity {
	@Id
	private String appUid;
	private String cardId;
	private double price;
	private Date datePay;
	private String approve;
}
