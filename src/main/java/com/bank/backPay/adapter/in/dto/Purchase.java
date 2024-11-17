package com.bank.backPay.adapter.in.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Purchase {
	private String appUid;
	private String cardId;
	private int price;
	private Date datePay;
	private String approve;
}
