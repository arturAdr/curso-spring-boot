package com.arturribeiro.cursomc.domain.enums;

public enum OrderStatus {
	
	PENDING(0, "Pendente"),
	PAID(1, "Pago"),
	CANCELLED(2, "Cancelado");

	private int cod;
	private String description;
	
	private OrderStatus(int cod, String descricao) {
		 this.cod = cod;
		 this.description = descricao;
	 }

	public int getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}

	public static OrderStatus toEnum(Integer id) {

		if (id == null) {
			 return null;
		}
	
		for (OrderStatus x : OrderStatus.values()) {
			 if (id.equals(x.getCod())) {
				 return x;
			 }
		 }

		throw new IllegalArgumentException("Id inválido " + id);
	} 
	
}
