package com.arturribeiro.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.arturribeiro.cursomc.domain.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class PaymentWithBillet extends Payment {

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date expirationDate;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date paymentDate;
	
	PaymentWithBillet() {
	}

	public PaymentWithBillet(Integer id, OrderStatus orderStatus, Order order, Date expirationDate, Date paymentDate) {
		super(id, orderStatus, order);
		this.expirationDate = expirationDate;
		this.paymentDate = paymentDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	
	
}
