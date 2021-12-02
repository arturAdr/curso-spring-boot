package com.arturribeiro.cursomc.domain;

import javax.persistence.Entity;

import com.arturribeiro.cursomc.domain.enums.OrderStatus;

@Entity
public class CardPayment extends Payment {

	private Integer numberOfInstallments;
	
	public CardPayment() {
		
	}

	public CardPayment(Integer id, OrderStatus orderStatus, Order order, Integer numberOfInstallments) {
		super(id, orderStatus, order);
		this.numberOfInstallments = numberOfInstallments;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}
	
	
	
}
