package com.bank.backPay.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.backPay.adapter.out.entity.PurchaseEntity;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, String> {

}
