package com.bank.backPay.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.backPay.adapter.out.entity.CardEntity;

public interface CardRepository extends JpaRepository<CardEntity,String>{

}
